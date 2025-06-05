package com.team_3.nursing_care.domain.attendance.entity;


import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "attendance_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "attendacne_explation_id")
    private AttendanceExplation attendanceExplain;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CheckInStatus checkInStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CheckOutStatus checkOutStatus;

    @Builder
    public AttendanceLog(Long attendanceId,
                         Member member,
                         AttendanceExplation attendanceExplain,
                         LocalDateTime checkIn,
                         LocalDateTime checkOut,
                         CheckInStatus checkInStatus,
                         CheckOutStatus checkOutStatus) {
        this.attendanceId = attendanceId;
        this.member = member;
        this.attendanceExplain= attendanceExplain;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.checkInStatus = checkInStatus;
        this.checkOutStatus = checkOutStatus;
    }

    public void setAttendanceExplation(AttendanceExplation attendanceExplain) {
        this.attendanceExplain = attendanceExplain;
    }
}
