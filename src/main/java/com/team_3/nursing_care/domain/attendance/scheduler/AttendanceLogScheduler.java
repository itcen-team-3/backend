package com.team_3.nursing_care.domain.attendance.scheduler;

import com.team_3.nursing_care.common.exception.CustomException;
import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.http.HttpStatusCode;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceLogScheduler {

    private final AttendanceLogRepository attendanceLogRepository;
    private final ScheduleRepository scheduleRepository;


    @Scheduled(cron = "0 47 13 * * ?")
    public void attendanceLogSchedule() {
        List<AttendanceLog> saveList = new ArrayList<>();
        AttendanceLog at = attendanceLogRepository.findById(1L).orElse(null);

        attendanceLogRepository.findAllForSchedule().forEach(al -> {
            Schedule schedule = scheduleRepository.findByAttendanceLog(al.getMember().getMemberId(), al.getPatientId(), al.getCheckIn().toLocalDate()).orElseThrow(() -> new CustomException(HttpStatusCode.NO_CONTENT, "로그 기록 상 일치하는 것이 되는 스케줄이 없다."));

            if (schedule.getStartTime().after(Time.valueOf(al.getCheckIn().toLocalTime())) ||
                    schedule.getStartTime().equals(Time.valueOf(al.getCheckIn().toLocalTime())))
                al.updateCheckInStatus(CheckInStatus.ON_TIME);
            else al.updateCheckInStatus(CheckInStatus.LATE);

            if (Time.valueOf(al.getCheckOut().toLocalTime()).before(schedule.getEndTime()) ||
                    Time.valueOf(al.getCheckOut().toLocalTime()).equals(schedule.getEndTime()))
                al.updateCheckOutStatus(CheckOutStatus.ON_TIME);
            else al.updateCheckOutStatus(CheckOutStatus.EARLY_LEAVE);

            saveList.add(al);
        });

        attendanceLogRepository.saveAll(saveList);
    }
}
