package com.team_3.nursing_care.domain.care_log.service;

import com.team_3.nursing_care.common.exception.CareLogException;
import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareLogDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareLogDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDetailDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDto;
import com.team_3.nursing_care.domain.care_log.entity.CareDetail;
import com.team_3.nursing_care.domain.care_log.entity.CareItem;
import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import com.team_3.nursing_care.domain.care_log.entity.CareLogImage;
import com.team_3.nursing_care.domain.care_log.repository.CareItemRepository;
import com.team_3.nursing_care.domain.care_log.repository.CareLogRepository;
import com.team_3.nursing_care.domain.care_log.repository.custom.CareLogCustomRepository;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.http.HttpStatusCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareLogDto.CareItemDto;
import static com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus.ONGOING;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "care_log_service")
public class CareLogServiceImpl implements CareLogService {

    private final S3Service s3Service;
    private final CareLogRepository careLogRepository;
    private final CareItemRepository careItemRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CareLogCustomRepository carLogCustomRepository;
    private final AttendanceLogRepository attendanceLogRepository;
    private final CompanyRepository companyRepository;


    @Override
    @Transactional
    public void postCareLog(ReqCreateCareLogDto reqCareLogDto, CustomUserDetails userDetails) {
        Member patient = memberRepository.findById(reqCareLogDto.getPatientId()).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "not found patient id: " + reqCareLogDto.getPatientId()));
        Member careGiver = memberRepository.findById(userDetails.getMemberId()).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "not found member by id: " + userDetails.getMemberId()));

        checkSchedule(careGiver);
        checkNfc(careGiver, patient);

        String signS3Key = s3Service.uploadSignFile(reqCareLogDto.getSignFile());
        String signUrl = s3Service.getFileUrl(signS3Key);

        if (!reqCareLogDto.getCareGiverId().equals(userDetails.getMemberId()))
            throw new CareLogException(HttpStatusCode.BAD_REQUEST, "Request body id and authentication object id do not match.");

        List<CareLogImage> careLogImageList = processingImageFile(reqCareLogDto.getImageFileList());
        List<CareDetail> careDetailList = processingCareDetailAndItem(reqCareLogDto.getCareItemList());

        CareLog careLog = CareLog.create(careGiver, patient.getMemberId(), patient.getMemberName(), signUrl, reqCareLogDto.getDescription());
        careLogImageList.forEach(careLog::connectCareLogImage);
        careDetailList.forEach(careLog::connectCareDetail);

        careLogRepository.save(careLog);

        log.info("돌봄 일지 생성 완료: {}", careLog.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResCareLogDto> getCareLogPage(LocalDate date, Pageable pageable, CustomUserDetails userDetails) {
        return carLogCustomRepository.getCareLogPage(date, pageable, userDetails, checkAdmin(userDetails));
    }


    @Override
    @Transactional(readOnly = true)
    public ResCareLogDetailDto getCareLogById(Long id, CustomUserDetails userDetails) {
        CareLog careLog = careLogRepository.findById(id).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "not found care log by id: " + id));
        checkAdminAndAuthorPatient(careLog, userDetails);
        CareLog joinCareLog = careLogRepository.findByIdJoinEntity(id).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "EntityGraph Error..?" + id));
        return ResCareLogDetailDto.create(joinCareLog);
    }

    @Override
    @Transactional
    public void updateCareLogById(Long id, ReqUpdateCareLogDto reqUpdateCareLogDto, CustomUserDetails userDetails) {
        CareLog careLog = careLogRepository.findById(id).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "not found care log by id: " + id));
        checkAdminAndAuthorPatient(careLog, userDetails);

        String signFileUrl = null;
        if (reqUpdateCareLogDto.getSignFile() != null && reqUpdateCareLogDto.getSignFile().isEmpty()) {
            String signS3Key = s3Service.uploadSignFile(reqUpdateCareLogDto.getSignFile());
            signFileUrl = s3Service.getFileUrl(signS3Key);
        }

        List<CareLogImage> careLogImageList = processingImageFile(reqUpdateCareLogDto.getImageFileList());
        List<CareDetail> careDetailList = processingCareDetailAndItem(reqUpdateCareLogDto.getCareItemList());

        careLogRepository.save(careLog.update(signFileUrl, reqUpdateCareLogDto.getDescription(), careLogImageList, careDetailList));
    }

    @Override
    @Transactional
    public void deleteCareLogById(Long id, CustomUserDetails userDetails) {
        CareLog careLog = careLogRepository.findById(id).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "not found care log by id: " + id));
        checkAdminAndAuthorPatient(careLog, userDetails);
        careLog.softDelete(userDetails.getMemberId());
    }

    private List<CareLogImage> processingImageFile(List<MultipartFile> imageFileList) {
        List<CareLogImage> careLogImageList = new ArrayList<>();
        if (imageFileList != null && !imageFileList.isEmpty()) {
            List<String> s3KeyList = s3Service.uploadImageFileList(imageFileList);

            for (String s3Key : s3KeyList)
                careLogImageList.add(CareLogImage.create(s3Service.getFileUrl(s3Key)));
        }
        return careLogImageList;
    }

    private void checkAdminAndAuthorPatient(CareLog careLog, CustomUserDetails userDetails) {
        boolean isAdmin = userDetails.getRoles().contains(Role.ADMIN);
        if (!isAdmin && !careLog.getCareGiver().getMemberId().equals(userDetails.getMemberId()) && !careLog.getPatientId().equals(userDetails.getMemberId()))
            throw new CareLogException(HttpStatusCode.BAD_REQUEST, "care log does not belong to member");
    }

    private List<CareDetail> processingCareDetailAndItem(List<CareItemDto> careItemDtoList) {
        List<Integer> careItemIdList = careItemDtoList.stream().map(CareItemDto::getCareItemId).toList();
        List<CareItem> careItemList = careItemRepository.findByIdIn(careItemIdList);

        Map<Integer, CareItem> careItemMap = careItemList.stream()
                .collect(Collectors.toMap(CareItem::getId, Function.identity()));

        return careItemDtoList.stream().map(itemDto -> {
            Integer careItemId = itemDto.getCareItemId();
            CareItem careItem = careItemMap.get(careItemId);

            if (careItem == null)
                throw new CareLogException(HttpStatusCode.NOT_FOUND, "not found care item by id: " + careItemId);

            return CareDetail.create(itemDto, careItem);
        }).toList();
    }

    private void checkSchedule(Member careGiver) {
        Schedule schedule = scheduleRepository.findByMember(careGiver).orElse(null);
        int todayBit = 1 << (LocalDate.now().getDayOfWeek().getValue() - 1);

        if (schedule == null) throw new CareLogException(HttpStatusCode.NOT_FOUND, "not found schedule");
        if (!schedule.getStatus().equals(ONGOING))
            throw new CareLogException(HttpStatusCode.BAD_REQUEST, "schedule is not ongoing");
        if ((schedule.getWorkDay() & todayBit) == 0)
            throw new CareLogException(HttpStatusCode.BAD_REQUEST, "schedule is not work day");
    }

    private void checkNfc(Member careGiver, Member patient) {
        LocalDate today = LocalDate.now();
        attendanceLogRepository.findByMemberAndCheckInDate(careGiver, patient.getMemberId(), today.atStartOfDay(), today.atStartOfDay().plusDays(1)).orElseThrow(() -> new CareLogException(HttpStatusCode.BAD_REQUEST, "not found nfc checking log"));
    }

    private Company checkAdmin(CustomUserDetails userDetails) {
        Company company = null;
        if (userDetails.getRoles().contains(Role.ADMIN))
            company = companyRepository.findById(userDetails.getCompanyId()).orElseThrow(() -> new CareLogException(HttpStatusCode.NOT_FOUND, "not found company id: " + userDetails.getCompanyId()));
        return company;
    }


}
