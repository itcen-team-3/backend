package com.team_3.nursing_care.domain.attendance.service;

import com.team_3.nursing_care.common.exception.NfcException;
import com.team_3.nursing_care.domain.attendance.constant.ApproveType;
import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.dto.request.UpdateApprovementTypeReqDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceAdminListResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceAdminResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverListResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverResDto;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceExplation;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceExplationRepository;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AttendanceExplationServiceImpl implements AttendanceExplationService {

    private final AttendanceExplationRepository attendanceExplationRepository;
    private final AttendanceLogRepository attendanceLogRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createAttendanceExplation(Long caregiverId, CreateAttendanceExplationReqDto createAttendanceExplationReqDto) {

        Member careGiver = memberRepository.findById(caregiverId).orElseThrow(() -> new NfcException(HttpStatusCode.NOT_FOUND, "Member not found"));

        AttendanceLog attendanceLog = null;
        log.info("checkinoutstatus: {}", createAttendanceExplationReqDto.getCheckInOutStatus());
        if(createAttendanceExplationReqDto.getCheckInOutStatus().equals("출근")) {
            attendanceLog = attendanceLogRepository.findAttendanceLog_CheckIn(careGiver,
                    createAttendanceExplationReqDto.getPatientId(),
                    createAttendanceExplationReqDto.getAttendanceDate().getYear(),
                    createAttendanceExplationReqDto.getAttendanceDate().getMonthValue(),
                    createAttendanceExplationReqDto.getAttendanceDate().getDayOfMonth()
            );
        }else if(createAttendanceExplationReqDto.getCheckInOutStatus().equals("퇴근")) {
            attendanceLog = attendanceLogRepository.findAttendanceLog_CheckOut(careGiver,
                    createAttendanceExplationReqDto.getPatientId(),
                    createAttendanceExplationReqDto.getAttendanceDate().getYear(),
                    createAttendanceExplationReqDto.getAttendanceDate().getMonthValue(),
                    createAttendanceExplationReqDto.getAttendanceDate().getDayOfMonth()
            );
        }

        attendanceExplationRepository.save(
                AttendanceExplation.toEntity(attendanceLog, createAttendanceExplationReqDto)
        );
    }

    @Override
    public AttendanceCaregiverListResDto getAttendanceExplationList(Long caregiverId) {

        List<AttendanceExplation> attendanceLogs = attendanceExplationRepository.findAllByCaregiverId(caregiverId);

        List<AttendanceCaregiverResDto> attendanceExplations = attendanceLogs.stream()
                .map(attendanceExplation ->
                    AttendanceCaregiverResDto.builder()
                            .attendanceExplationId(attendanceExplation.getAttendanceExplationId())
                            .explation(attendanceExplation.getExplations())
                            .rejectReason(attendanceExplation.getRejectReason())
                            .submitDateTime(attendanceExplation.getCreateDate())
                            .attendanceDate(attendanceExplation.getAttendanceDate())
                            .attendanceTime(attendanceExplation.getAttendanceTime())
                            .approveStatus(attendanceExplation.getApproveType().getApproveTypeName())
                            .attendanceStatus(attendanceExplation.getCheckInOutStatus())
                            .build()
                ).toList();

        return AttendanceCaregiverListResDto.from(attendanceExplations);
    }

    @Override
    public AttendanceAdminListResDto getAttendanceExplationListByAdmin(Long adminId) {

        List<Long> caregiverIds = memberRepository.findByCompany_CompanyIdAndRole(
                        memberRepository.findById(adminId)
                                .orElseThrow(() -> new IllegalArgumentException("관리자 정보가 없습니다."))
                                .getCompany().getCompanyId(),
                        Role.CAREGIVER
                ).stream()
                .map(Member::getMemberId)
                .toList();

        List<AttendanceExplation> attendanceExplanations = attendanceExplationRepository.findByCaregiverIds(caregiverIds);

        List<AttendanceAdminResDto> attendanceAdminResponseList = attendanceExplanations.stream()
                .map(explanation -> {
                    AttendanceLog log = explanation.getAttendanceLog();
                    Member caregiver = log.getMember();

                    return AttendanceAdminResDto.builder()
                            .attendanceExplationId(explanation.getAttendanceExplationId())
                            .caregiverName(caregiver.getMemberName())
                            .approveStatus(explanation.getApproveType().getApproveTypeName())
                            .explation(explanation.getExplations())
                            .submitDateTime(explanation.getCreateDate())
                            .build();
                })
                .toList();

        return AttendanceAdminListResDto.from(attendanceAdminResponseList);

    }

    @Transactional
    @Override
    public void updateAttendanceApprovementType(Long attendanceExplationId,
                                                UpdateApprovementTypeReqDto updateApprovementTypeReqDto) {

        AttendanceExplation explation = attendanceExplationRepository.findById(attendanceExplationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 소명이 존재하지 않습니다."));

        explation.updateApproval(ApproveType.from(updateApprovementTypeReqDto.getApproveType()), updateApprovementTypeReqDto.getRejectReason());
    }
}
