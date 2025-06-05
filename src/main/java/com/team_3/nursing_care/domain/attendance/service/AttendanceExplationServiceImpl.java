package com.team_3.nursing_care.domain.attendance.service;

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
    public void createAttendanceExplation(CreateAttendanceExplationReqDto createAttendanceExplationReqDto) {
        AttendanceExplation explation = attendanceExplationRepository.save(
                AttendanceExplation.toEntity(createAttendanceExplationReqDto)
        );

        AttendanceLog attendanceLog = attendanceLogRepository.findById(createAttendanceExplationReqDto.getAttendanceId())
                .orElseThrow(() -> new IllegalArgumentException("출퇴근 기록을 찾을 수 없습니다."));

        attendanceLog.setAttendanceExplation(explation);
    }

    @Override
    public AttendanceCaregiverListResDto getAttendanceExplationList(Long caregiverId) {
        List<AttendanceLog> attendanceLogs = attendanceLogRepository.findByMember_memberId(caregiverId);

        List<AttendanceCaregiverResDto> attendanceExplation = attendanceLogs.stream()
                .map(AttendanceLog::getAttendanceExplain)
                .filter(Objects::nonNull)
                .map(explation -> AttendanceCaregiverResDto.builder()
                        .attendanceExplationId(explation.getAttendanceExplationId())
                        .approveStatus(explation.getApproveType().getApproveTypeName())
                        .explation(explation.getExplations())
                        .rejectReason(explation.getRejectReason())
                        .submitDateTime(explation.getCreateDate())
                        .build())
                .toList();

        return AttendanceCaregiverListResDto.from(attendanceExplation);
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

        List<AttendanceLog> attendanceLogsWithExplanation = attendanceLogRepository
                .findAllByMember_MemberIdIn(caregiverIds).stream()
                .filter(log -> log.getAttendanceExplain() != null)
                .toList();

        List<AttendanceAdminResDto> attendanceAdminResponseList = attendanceLogsWithExplanation.stream()
                .map(log -> {
                    AttendanceExplation explanation = log.getAttendanceExplain();
                    return AttendanceAdminResDto.builder()
                            .attendanceExplationId(explanation.getAttendanceExplationId())
                            .caregiverName(log.getMember().getMemberName())
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
