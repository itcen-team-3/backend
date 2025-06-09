package com.team_3.nursing_care.domain.schedule.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleReadResDto {

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

    @Builder
    public ScheduleReadResDto(String caregiverName,
                              String patientName,
                              Boolean isFamily,
                              LocalDate startDate,
                              LocalDate endDate,
                              Time startTime,
                              Time endTime,
                              int workDay,
                              String paymentType,
                              int paymentForHour) {
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
    }

    public static ScheduleReadResDto from(Schedule schedule, Member caregiver, Member patient){
        return ScheduleReadResDto.builder()
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
                .build();
    }
}
