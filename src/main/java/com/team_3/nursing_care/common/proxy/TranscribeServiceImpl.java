package com.team_3.nursing_care.common.proxy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

@Slf4j(topic = "transcription service")
@Service
@RequiredArgsConstructor
public class TranscribeServiceImpl implements TranscribeService {

    private final TranscribeClient transcribeClient;
    private final S3Service s3Service;
    private final ObjectMapper objectMapper;

    @Value("${transcribe.polling-interval-ms}")
    private Long pollingIntervalMs;
    @Value("${transcribe.polling-timeout-sec}")
    private Long pollingTimeoutSec;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;
    private final String TRANSCRIPTION_PATH = "transcription";

    public String startTranscribeJob(String voiceFilePath) {
        String JOB_NAME = "nursing-care-transcription-job-" + System.currentTimeMillis();

        Media media = Media.builder()
                .mediaFileUri("s3://" + bucketName + "/" + voiceFilePath)
                .build();

        StartTranscriptionJobRequest request = StartTranscriptionJobRequest.builder()
                .transcriptionJobName(JOB_NAME)
                .media(media)
                .languageCode(LanguageCode.KO_KR)
                .outputBucketName(bucketName)
                .outputKey(TRANSCRIPTION_PATH + "/" + JOB_NAME + ".json")
                .build();

        transcribeClient.startTranscriptionJob(request);
        return waitForCompletion(JOB_NAME);
    }

    private String waitForCompletion(String jobName) {
        GetTranscriptionJobRequest getJobRequest = GetTranscriptionJobRequest.builder()
                .transcriptionJobName(jobName)
                .build();

        TranscriptionJobStatus status = null;
        long startTime = System.currentTimeMillis();
        Duration timeout = Duration.ofSeconds(pollingTimeoutSec);

        try {
            do {
                GetTranscriptionJobResponse getJobResponse = transcribeClient.getTranscriptionJob(getJobRequest);
                status = getJobResponse.transcriptionJob().transcriptionJobStatus();
                log.debug("작업 [{}] 현재 상태: {}", jobName, status);

                if (status == TranscriptionJobStatus.COMPLETED) {
                    log.info("작업 [{}] 완료!", jobName);

                    String s3Key = TRANSCRIPTION_PATH + "/" + jobName + ".json";
                    log.debug("결과 파일: {}", s3Key);

                    try {
                        InputStream download = s3Service.download(s3Key);
                        log.debug("Load S3 Instance Stream");

                        JsonNode rootNode = objectMapper.readTree(download);
                        log.debug("Json Parsing Result: {}", rootNode);

                        JsonNode transcriptsNode = rootNode.path("results").path("transcripts");

                        if (transcriptsNode.isArray() && !transcriptsNode.isEmpty()) {
                            String transcribedText = transcriptsNode.get(0).path("transcript").asText();
                            log.debug("텍스트 추출 성공.");

                            log.info("Extracted Transcription Data: {}", rootNode);
                            return transcribedText;
                        } else {
                            log.error("JSON 결과에서 'results.transcripts[0].transcript' 경로를 찾을 수 없습니다. JSON 구조 확인 필요. URI: {}", s3Key);
                            throw new RuntimeException("Could not find transcript in the result JSON for URI: " + s3Key);
                        }

                    } catch (IOException e) {
                        log.error("결과 파일 다운로드 또는 JSON 파싱 중 IO 오류: {}", s3Key, e);
                        throw new RuntimeException("Error reading or parsing transcription result file: " + s3Key, e);
                    } catch (SdkClientException e) {
                        log.error("S3 클라이언트 오류 발생 (결과 다운로드): {}", s3Key, e);
                        throw new RuntimeException("S3 client error while downloading transcription result: " + s3Key, e);
                    } catch (Exception e) {
                        log.error("결과 파일 파싱 중 예상치 못한 오류: {}", s3Key, e);
                        throw new RuntimeException("Unexpected error parsing transcription result: " + s3Key, e);
                    }

                } else if (status == TranscriptionJobStatus.FAILED) {
                    String failureReason = getJobResponse.transcriptionJob().failureReason();
                    log.error("Failure reason: {}", failureReason);
                    throw new RuntimeException("Failure reason: " + failureReason);
                }

                if (System.currentTimeMillis() - startTime > timeout.toMillis()) {
                    log.error("Transcription Job Timeout: {} exceed {}", jobName, pollingTimeoutSec);
                    throw new RuntimeException("Transcription Job Timeout: " + pollingTimeoutSec);
                }

                Thread.sleep(pollingIntervalMs);
            } while (status == TranscriptionJobStatus.IN_PROGRESS || status == TranscriptionJobStatus.QUEUED);
        } catch (Exception e) {
            log.error("catch 문까지 옴 로직 다시봐라");
            e.printStackTrace();
        }

        throw new RuntimeException("마지막 {} 로직 개쳐망함.");
    }
}
