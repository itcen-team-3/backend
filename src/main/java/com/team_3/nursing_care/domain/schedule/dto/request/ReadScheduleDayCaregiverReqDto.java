package com.team_3.nursing_care.domain.schedule.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadScheduleDayCaregiverReqDto {

    private LocalDate scheduleDate;

    @Builder
    public ReadScheduleDayCaregiverReqDto(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}