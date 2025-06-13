package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleWeekCaregiverResDto {

    private String patientName;
    private Long scheduleId;
    private LocalDate scheduleDate;
    private Time endTime;
    private Time startTime;
    private String patientAddress;

    @Builder
    public ScheduleWeekCaregiverResDto(Long scheduleId,
                                       String patientName,
                                       Time endTime,
                                       Time startTime,
                                       LocalDate scheduleDate,
                                       String patientAddress) {
        this.patientName=patientName;
        this.scheduleId = scheduleId;
        this.endTime = endTime;
        this.startTime = startTime;
        this.scheduleDate=scheduleDate;
        this.patientAddress = patientAddress;
    }
}
