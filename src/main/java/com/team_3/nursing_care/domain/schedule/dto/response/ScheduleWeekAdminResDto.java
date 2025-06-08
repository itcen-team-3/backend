package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleWeekAdminResDto {

    private Long caregiverId;
    private Long patientId;
    private Long scheduleId;
    private LocalDate scheduleDate;
    private String patientName;
    private Time startTime;
    private Time endTime;

    @Builder
    public ScheduleWeekAdminResDto(Long scheduleId,
                                   Long caregiverId,
                                   Long patientId,
                                   LocalDate scheduleDate,
                                   String patientName,
                                   Time startTime,
                                   Time endTime) {
        this.scheduleId = scheduleId;
        this.caregiverId=caregiverId;
        this.patientId=patientId;
        this.scheduleDate = scheduleDate;
        this.patientName = patientName;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
