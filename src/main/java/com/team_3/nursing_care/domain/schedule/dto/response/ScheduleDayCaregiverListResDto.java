package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleDayCaregiverListResDto {

    private List<ScheduleDayCaregiverResDto> scheduleDay;

    @Builder
    public ScheduleDayCaregiverListResDto(List<ScheduleDayCaregiverResDto> scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public static ScheduleDayCaregiverListResDto from(List<ScheduleDayCaregiverResDto> schedule) {
        return ScheduleDayCaregiverListResDto.builder()
                .scheduleDay(schedule)
                .build();
    }

}