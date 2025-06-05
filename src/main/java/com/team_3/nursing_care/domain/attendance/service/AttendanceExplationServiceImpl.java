package com.team_3.nursing_care.domain.attendance.service;

import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverListResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverResDto;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceExplation;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceExplationRepository;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AttendanceExplationServiceImpl implements AttendanceExplationService {

    private final AttendanceExplationRepository attendanceExplationRepository;
    private final AttendanceLogRepository attendanceLogRepository;

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

        List<AttendanceCaregiverResDto> resDtos = attendanceLogs.stream()
                .map(AttendanceLog::getAttendanceExplain)
                .filter(Objects::nonNull) // 소명이 있는 것만
                .map(explation -> AttendanceCaregiverResDto.builder()
                        .attendanceExplationId(explation.getAttendanceExplationId())
                        .approveStatus(explation.getApproveType().name()) // enum → string
                        .explation(explation.getExplations())
                        .rejectReason(explation.getRejectReason()) // 이 필드 존재해야 함
                        .submitDateTime(explation.getCreateDate())   // BaseEntity에 있다고 가정
                        .build())
                .toList();

        return AttendanceCaregiverListResDto.from(resDtos);
    }
}
