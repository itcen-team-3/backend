package com.team_3.nursing_care.domain.salary.dto.response;

import com.team_3.nursing_care.domain.salary.constant.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SalaryScheduleResDto {

    private Long caregiverId;
    private Long salaryId;
    private String caregiverName;
    private String yearMonth;
    private int totalWorkTime;
    private int paymentPerHour;
    private int totalCost;
    private PaymentStatus paymentStatus;

    @Builder
    public SalaryScheduleResDto(Long salaryId,
                                Long caregiverId,
                                String caregiverName,
                                String yearMonth,
                                int totalWorkTime,
                                int paymentPerHour,
                                int totalCost,
                                PaymentStatus paymentStatus) {
        this.caregiverId = caregiverId;
        this.salaryId = salaryId;
        this.caregiverName = caregiverName;
        this.yearMonth = yearMonth;
        this.totalWorkTime = totalWorkTime;
        this.paymentPerHour = paymentPerHour;
        this.totalCost = totalCost;
        this.paymentStatus = paymentStatus;
    }
}
