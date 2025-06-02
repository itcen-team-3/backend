package com.team_3.nursing_care.domain.schedule.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.schedule.constant.PaymentType;
import com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    @Column(nullable = false)
    private int workDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(nullable = false)
    private int paymentForHour;

    @Column(nullable = false)
    private boolean isFamily;

    @Column(nullable = false)
    private String patient;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private String patientAddress;

    @Builder
    public Schedule(Long scheduleId,
                    Member member,
                    LocalDate startDate,
                    LocalDate endDate,
                    Time startTime,
                    Time endTime,
                    int workDay,
                    ScheduleStatus status,
                    int paymentForHour,
                    boolean isFamily,
                    String patient,
                    PaymentType paymentType,
                    String patientAddress) {
        this.scheduleId = scheduleId;
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDay = workDay;
        this.status = status;
        this.paymentForHour = paymentForHour;
        this.isFamily = isFamily;
        this.patient = patient;
        this.paymentType = paymentType;
        this.patientAddress = patientAddress;
    }

    public void updateIsDelete(Boolean isDelete){
        this.isDeleted=isDelete;
    }
}
