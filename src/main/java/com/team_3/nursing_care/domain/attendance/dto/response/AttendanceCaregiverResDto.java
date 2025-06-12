package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class AttendanceCaregiverResDto {

    private Long attendanceExplationId;
    private String approveStatus;
    private String explation;
    private String rejectReason;
    private LocalDateTime submitDateTime;
    private LocalDate attendanceDate;
    private LocalTime attendanceTime;
    private String attendanceStatus;

    @Builder
    public AttendanceCaregiverResDto(Long attendanceExplationId,
                                     String approveStatus,
                                     String explation,
                                     String attendanceStatus,
                                     String rejectReason,
                                     LocalDateTime submitDateTime,
                                     LocalDate attendanceDate,
                                     LocalTime attendanceTime) {
        this.attendanceExplationId = attendanceExplationId;
        this.attendanceStatus = attendanceStatus;
        this.approveStatus = approveStatus;
        this.explation = explation;
        this.rejectReason = rejectReason;
        this.submitDateTime = submitDateTime;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }
}
