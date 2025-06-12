package com.team_3.nursing_care.domain.attendance.entity;


import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.attendance.constant.ApproveType;
import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "attendance_explation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceExplation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceExplationId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "attendance_id")
    private AttendanceLog attendanceLog;


    @Column(length = 500)
    private String explations;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApproveType approveType;

    private String rejectReason;
    private LocalTime attendanceTime;
    private LocalDate attendanceDate;
    private String checkInOutStatus;

    private Long caregiverId;

    @Builder
    public AttendanceExplation(Long attendanceExplationId,
                               AttendanceLog attendanceLog,
                               Long caregiverId,
                               String explations,
                               ApproveType approveType,
                               String rejectReason,
                               LocalTime attendanceTime,
                               LocalDate attendanceDate,
                               String checkInOutStatus) {
        this.attendanceExplationId = attendanceExplationId;
        this.attendanceLog = attendanceLog;
        this.explations = explations;
        this.approveType = approveType;
        this.caregiverId = caregiverId;
        this.rejectReason = rejectReason;
        this.attendanceTime = attendanceTime;
        this.attendanceDate = attendanceDate;
        this.checkInOutStatus = checkInOutStatus;
    }

    public static AttendanceExplation toEntity(AttendanceLog attendanceLog,
                                               CreateAttendanceExplationReqDto createAttendanceExplationReqDto){
        return AttendanceExplation.builder()
                .attendanceLog(attendanceLog)
                .caregiverId(attendanceLog.getMember().getMemberId())
                .attendanceDate(createAttendanceExplationReqDto.getAttendanceDate())
                .attendanceTime(createAttendanceExplationReqDto.getAttendanceTime())
                .explations(createAttendanceExplationReqDto.getExplation())
                .approveType(ApproveType.WAITING)
                .checkInOutStatus(createAttendanceExplationReqDto.getCheckInOutStatus())
                .build();
    }

    public void updateApproval(ApproveType approveType, String rejectReason) {
        this.approveType = approveType;
        this.rejectReason = (approveType == ApproveType.REJECTED) ? rejectReason : null;
    }

}
