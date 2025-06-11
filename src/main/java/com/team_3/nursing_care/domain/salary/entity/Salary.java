package com.team_3.nursing_care.domain.salary.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.salary.constant.PaymentStatus;
import com.team_3.nursing_care.domain.schedule.constant.PaymentType;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.team_3.nursing_care.domain.salary.constant.PaymentStatus.NON_PAYMENT;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "salary")
public class Salary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long careGiverId;
    private Long parentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer workHours;
    private Integer totalCost;
    private Integer costPerHour;
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public static Salary create(Schedule schedule, LocalDate startDate, Integer totalCost, double workHours) {
        return Salary.builder()
                .careGiverId(schedule.getMember().getMemberId())
                .parentId(schedule.getPatientId())
                .startDate(startDate)
                .endDate(startDate.plusDays(30))
                .workHours((int) workHours)
                .totalCost(totalCost)
                .costPerHour(schedule.getPaymentForHour())
                .status(NON_PAYMENT)
                .paymentType(schedule.getPaymentType())
                .schedule(schedule)
                .build();
        // TODO: 여기 고쳐야함
    }
}
