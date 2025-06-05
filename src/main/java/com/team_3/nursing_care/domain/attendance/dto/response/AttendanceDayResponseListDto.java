package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttendanceDayResponseListDto {

    private List<AttendanceDayResponseDto> attendanceDays;

    @Builder
    public AttendanceDayResponseListDto(List<AttendanceDayResponseDto> attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public static AttendanceDayResponseListDto from(List<AttendanceDayResponseDto> attendanceDays) {
        return AttendanceDayResponseListDto.builder()
                .attendanceDays(attendanceDays)
                .build();
    }
}
