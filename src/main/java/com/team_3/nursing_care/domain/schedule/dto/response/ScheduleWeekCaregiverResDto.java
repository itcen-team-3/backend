package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleWeekCaregiverResDto {

    private Long scheduleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int workDay;
    private Time endTime;
    private Time startTime;
    private String patientAddress;

    @Builder
    public ScheduleWeekCaregiverResDto(Long scheduleId,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       int workDay,
                                       Time endTime,
                                       Time startTime,
                                       String patientAddress) {
        this.scheduleId = scheduleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDay = workDay;
        this.endTime = endTime;
        this.startTime = startTime;
        this.patientAddress = patientAddress;
    }
}
