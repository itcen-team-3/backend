package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AttendanceAdminResDto {

    private Long attendanceExplationId;
    private String caregiverName;
    private String approveStatus;
    private String explation;
    private LocalDateTime submitDateTime;

    @Builder
    public AttendanceAdminResDto(Long attendanceExplationId,
                                 String caregiverName,
                                 String approveStatus,
                                 String explation,
                                 LocalDateTime submitDateTime) {
        this.attendanceExplationId = attendanceExplationId;
        this.caregiverName = caregiverName;
        this.approveStatus = approveStatus;
        this.explation = explation;
        this.submitDateTime = submitDateTime;
    }
}
