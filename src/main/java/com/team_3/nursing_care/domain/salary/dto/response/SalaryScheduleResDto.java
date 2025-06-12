package com.team_3.nursing_care.domain.salary.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.salary.constant.PaymentStatus;
import com.team_3.nursing_care.domain.salary.entity.Salary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SalaryScheduleResDto {

    private Long caregiverId;
    private Long salaryId;
    private String caregiverName;
    private int year;
    private int month;
    private int totalWorkTime;
    private int paymentPerHour;
    private int totalCost;
    private PaymentStatus paymentStatus;

    @Builder
    public SalaryScheduleResDto(Long salaryId,
                                Long caregiverId,
                                String caregiverName,
                                int year,
                                int month,
                                int totalWorkTime,
                                int paymentPerHour,
                                int totalCost,
                                PaymentStatus paymentStatus) {
        this.caregiverId = caregiverId;
        this.salaryId = salaryId;
        this.caregiverName = caregiverName;
        this.year = year;
        this.month = month;
        this.totalWorkTime = totalWorkTime;
        this.paymentPerHour = paymentPerHour;
        this.totalCost = totalCost;
        this.paymentStatus = paymentStatus;
    }

    public static SalaryScheduleResDto create(Salary salary, Member member,int year, int month ) {
        return SalaryScheduleResDto.builder()
                .caregiverId(salary.getCareGiverId())
                .salaryId(salary.getId())
                .caregiverName(member.getMemberName())
                .year(year)
                .month(month)
                .totalWorkTime(salary.getWorkHours())
                .paymentPerHour(salary.getCostPerHour())
                .totalCost(salary.getTotalCost())
                .paymentStatus(salary.getStatus())
                .build();
    }
}
