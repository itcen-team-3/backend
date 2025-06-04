package com.team_3.nursing_care.domain.schedule.dto.request;

import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.schedule.constant.PaymentType;
import com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
public class CreateScheduleRequestDto {

    private String patientName;
    private Long patientId;
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
    public CreateScheduleRequestDto(String patientName,
                                    Long patientId,
                                    Long caregiverId,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    Time startTime,
                                    Time endTime,
                                    int paymentForHour,
                                    int workDay,
                                    String paymentType,
                                    Boolean isFamily) {
        this.patientName = patientName;
        this.patientId = patientId;
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

    public Schedule toEntity(Member member, String patientAddress) {
        return Schedule.builder()
                .member(member)
                .patientId(patientId)
                .patient(patientName)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .paymentForHour(paymentForHour)
                .workDay(workDay)
                .paymentType(PaymentType.from(paymentType))
                .status(ScheduleStatus.PLANNED)
                .isFamily(isFamily)
                .patientAddress(patientAddress)
                .build();
    }
}
