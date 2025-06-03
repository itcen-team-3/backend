package com.team_3.nursing_care.domain.schedule.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
public class UpdateScheduleRequestDto {

    private Long patientId;
    private String patientName;
    private Long caregiverId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Time startTime;
    private Time endTime;
    private int paymentForHour;
    private int workDay;
    private String paymentType;
    private Boolean isFamily;

    @Builder
    public UpdateScheduleRequestDto(
            Long patientId,
            String patientName,
            Long caregiverId,
            LocalDate startDate,
            LocalDate endDate,
            Time startTime,
            Time endTime,
            int paymentForHour,
            int workDay,
            String paymentType,
            Boolean isFamily) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.caregiverId = caregiverId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.paymentForHour = paymentForHour;
        this.workDay = workDay;
        this.paymentType = paymentType;
        this.isFamily = isFamily;
    }

}