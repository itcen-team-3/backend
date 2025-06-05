package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AttendanceCaregiverResDto {

    private Long attendanceExplationId;
    private String approveStatus;
    private String explation;
    private String rejectReason;
    private LocalDateTime submitDateTime;

    @Builder
    public AttendanceCaregiverResDto(Long attendanceExplationId,
                                     String approveStatus,
                                     String explation,
                                     String rejectReason,
                                     LocalDateTime submitDateTime) {
        this.attendanceExplationId = attendanceExplationId;
        this.approveStatus = approveStatus;
        this.explation = explation;
        this.rejectReason = rejectReason;
        this.submitDateTime = submitDateTime;
    }
}
