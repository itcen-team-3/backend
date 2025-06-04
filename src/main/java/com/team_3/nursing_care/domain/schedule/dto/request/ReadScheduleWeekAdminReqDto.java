package com.team_3.nursing_care.domain.schedule.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReadScheduleWeekAdminReqDto {

    private LocalDate startDate;
    private List<CaregiverIdReqDto> caregiverIds;

}
