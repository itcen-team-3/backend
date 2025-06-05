package com.team_3.nursing_care.domain.attendance.service;

import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceDayResponseDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceDayResponseListDto;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AttendanceLogServiceImpl implements AttendanceLogService {

    private final AttendanceLogRepository attendanceLogRepository;

    @Override
    public AttendanceDayResponseListDto getDeAttendanceDay(Long caregiverId) {

        List<AttendanceLog> attendanceLog = attendanceLogRepository
                .findByMember_MemberIdAndCheckInStatusOrCheckOutStatus(caregiverId, CheckInStatus.LATE, CheckOutStatus.EARLY_LEAVE);

        List<AttendanceDayResponseDto> attendanceDays = attendanceLog.stream()
                .map(log -> AttendanceDayResponseDto.builder()
                        .attendanceDay(log.getCheckIn().toLocalDate())
                        .build())
                .distinct()
                .toList();

        return AttendanceDayResponseListDto.from(attendanceDays);
    }
}
