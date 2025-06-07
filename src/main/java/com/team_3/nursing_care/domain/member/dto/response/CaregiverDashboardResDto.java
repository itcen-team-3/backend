package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CaregiverDashboardResDto {

    private String caregiverName;
    private List<CaregiverScheduleResDto> schedules;

    @Builder
    public CaregiverDashboardResDto(String caregiverName, List<CaregiverScheduleResDto> schedules) {
        this.caregiverName = caregiverName;
        this.schedules = schedules;
    }


}
