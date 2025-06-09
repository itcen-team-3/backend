package com.team_3.nursing_care.domain.attendance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAttendanceExplationReqDto {

    private Long attendanceId;
    private String explation;

    @Builder
    public CreateAttendanceExplationReqDto(Long attendanceId, String explation) {
        this.attendanceId = attendanceId;
        this.explation = explation;
    }
}
