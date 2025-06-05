package com.team_3.nursing_care.common.proxy;

import com.team_3.nursing_care.common.exception.CareLogException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${spring.application.name}")
    private String prefix;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Client s3Client;

    @Override
    public String uploadVoiceFile(MultipartFile file) {
        checkEmptyFile(file);
        String s3Key = "voice/" + createS3Key(file);

        PutObjectRequest putObjectRequest = createPutObjectRequest(file, bucket, s3Key);
        upload(putObjectRequest, file);

        return s3Key;
    }

    @Override
    public String uploadBusinessRegistrationFile(MultipartFile file) {
        checkEmptyFile(file);
        String s3Key = "business_registration/" + createS3Key(file);

        PutObjectRequest putObjectRequest = createPutObjectRequest(file, bucket, s3Key);
        upload(putObjectRequest, file);

        return s3Key;
    }

    @Override
    public String uploadSignFile(MultipartFile file) {
        checkEmptyFile(file);
        String s3Key = "sign/" + createS3Key(file);

        PutObjectRequest putObjectRequest = createPutObjectRequest(file, bucket, s3Key);
        upload(putObjectRequest, file);

        return s3Key;
    }

    @Override
    public String uploadProfileFile(MultipartFile file) {
        checkEmptyFile(file);
        String s3Key = "profile/" + createS3Key(file);

        PutObjectRequest putObjectRequest = createPutObjectRequest(file, bucket, s3Key);
        upload(putObjectRequest, file);

        return s3Key;
    }

    @Override
    public List<String> uploadImageFileList(List<MultipartFile> imageFileList) {
        List<String> s3KeyList = new ArrayList<>();
        for (MultipartFile file : imageFileList) {
            String s3Key = "care_log/" + createS3Key(file);

            PutObjectRequest putObjectRequest = createPutObjectRequest(file, bucket, s3Key);
            upload(putObjectRequest, file);

            s3KeyList.add(s3Key);
        }

        return s3KeyList;
    }

    @Override
    public InputStream download(String s3Key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build();

        ResponseInputStream<?> s3Object = s3Client.getObject(getObjectRequest);
        log.debug("S3 download input stream: s3://" + bucket + "/" + s3Key);
        return s3Object;
    }

    @Override
    public String getFileUrl(String s3Key) {
        return String.format("s3://%s/%s", bucket, s3Key);
    }

    private void checkEmptyFile(MultipartFile file) {
        if (file.isEmpty()) throw new CareLogException(HttpStatusCode.BAD_REQUEST, "Required File");
    }

    private String createS3Key(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        int dotIndex = originalFileName != null ? originalFileName.lastIndexOf(".") : 0;
        if (dotIndex > 0) extension = originalFileName.substring(dotIndex);
        return prefix + "_" + UUID.randomUUID() + "_" + extension;
    }

    private PutObjectRequest createPutObjectRequest(MultipartFile file, String bucket, String s3Key) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
    }

    private void upload(PutObjectRequest request, MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            s3Client.putObject(request, RequestBody.fromInputStream(is, file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading file");
        }
    }
}