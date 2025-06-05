package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AttendanceDayResponseDto {

    private LocalDate attendanceDay;

    @Builder
    public AttendanceDayResponseDto(LocalDate attendanceDay) {
        this.attendanceDay = attendanceDay;
    }

}
