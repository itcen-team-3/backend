package com.team_3.nursing_care.domain.attendance.entity;


import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.attendance.constant.ApproveType;
import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "attendance_explation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceExplation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceExplationId;

    @Column(length = 500)
    private String explations;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApproveType approveType;

    private String rejectReason;

    @Builder
    public AttendanceExplation(Long attendanceExplationId,
                               String explations,
                               ApproveType approveType,
                               String rejectReason) {
        this.attendanceExplationId = attendanceExplationId;
        this.explations = explations;
        this.approveType = approveType;
        this.rejectReason = rejectReason;
    }

    public static AttendanceExplation toEntity(CreateAttendanceExplationReqDto createAttendanceExplationReqDto){
        return AttendanceExplation.builder()
                .explations(createAttendanceExplationReqDto.getExplation())
                .approveType(ApproveType.WAITING)
                .build();
    }
}
