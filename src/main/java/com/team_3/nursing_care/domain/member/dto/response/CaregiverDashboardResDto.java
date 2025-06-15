package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Getter
@NoArgsConstructor
public class CaregiverDashboardResDto {

    private String caregiverName;
    private Long patientId;
    private String patientName;
    private Time startTime;
    private Time endTime;
    private Boolean workStatus;

    @Builder
    public CaregiverDashboardResDto(String caregiverName, Long patientId, String patientName, Time startTime, Time endTime, Boolean workStatus) {
        this.caregiverName = caregiverName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workStatus = workStatus;
    }
}
