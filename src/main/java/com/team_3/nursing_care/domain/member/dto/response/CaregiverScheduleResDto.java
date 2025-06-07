package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@NoArgsConstructor
public class CaregiverScheduleResDto {

    private String patientName;
    private Time startTime;
    private Time endTime;
    private String attendanceStatus;

    @Builder
    public CaregiverScheduleResDto(String patientName,
                                   Time startTime,
                                   Time endTime,
                                   String attendanceStatus) {
        this.patientName = patientName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceStatus = attendanceStatus;
    }

}
