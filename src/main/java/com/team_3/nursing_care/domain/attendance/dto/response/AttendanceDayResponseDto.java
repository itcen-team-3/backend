package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AttendanceDayResponseDto {

    private Long attendanceId;
    private LocalDateTime deAttendanceDateTime;

    @Builder
    public AttendanceDayResponseDto(Long attendanceId, LocalDateTime deAttendanceDateTime) {
        this.attendanceId = attendanceId;
        this.deAttendanceDateTime = deAttendanceDateTime;
    }
}
