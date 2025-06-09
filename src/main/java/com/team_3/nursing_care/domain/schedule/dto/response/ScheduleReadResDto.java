package com.team_3.nursing_care.domain.schedule.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleReadResDto {

    private Long patientId;
    private Long caregiverId;
    private String caregiverName;
    private String patientName;
    private Boolean isFamily;
    private LocalDate startDate;
    private LocalDate endDate;
    private Time startTime;
    private Time endTime;
    private int workDay;
    private String paymentType;
    private int paymentForHour;
    private ScheduleStatus status;

    @Builder
    public ScheduleReadResDto(
                              Long patientId,
                              Long caregiverId,
                              String caregiverName,
                              String patientName,
                              Boolean isFamily,
                              LocalDate startDate,
                              LocalDate endDate,
                              Time startTime,
                              Time endTime,
                              int workDay,
                              String paymentType,
                              int paymentForHour,
                              ScheduleStatus status) {
        this.caregiverId=caregiverId;
        this.patientId=patientId;
        this.caregiverName = caregiverName;
        this.patientName = patientName;
        this.isFamily = isFamily;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDay = workDay;
        this.paymentType = paymentType;
        this.paymentForHour = paymentForHour;
        this.status=status;
    }

    public static ScheduleReadResDto from(Schedule schedule, Member caregiver, Member patient){
        return ScheduleReadResDto.builder()
                .caregiverId(schedule.getMember().getMemberId())
                .patientId(schedule.getPatientId())
                .caregiverName(caregiver.getMemberName())
                .patientName(patient.getMemberName())
                .isFamily(schedule.isFamily())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .workDay(schedule.getWorkDay())
                .paymentType(schedule.getPaymentType().getPaymentType())
                .paymentForHour(schedule.getPaymentForHour())
                .status(schedule.getStatus())
                .build();
    }
}
