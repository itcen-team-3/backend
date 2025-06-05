package com.team_3.nursing_care.domain.attendance.service;

import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceExplation;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceExplationRepository;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


        // 1. 소명 엔티티 생성 & 저장
        AttendanceExplation explation = attendanceExplationRepository.save(
                AttendanceExplation.toEntity(createAttendanceExplationReqDto)
        );

        // 2. 해당 출퇴근 로그 가져오기
        AttendanceLog attendanceLog = attendanceLogRepository.findById(createAttendanceExplationReqDto.getAttendanceId())
                .orElseThrow(() -> new IllegalArgumentException("출퇴근 기록을 찾을 수 없습니다."));

        log.info("attendanceLog: {}", attendanceLog);
        // 3. 출퇴근 로그에 소명 연결
        attendanceLog.setAttendanceExplation(explation);

    }
}
