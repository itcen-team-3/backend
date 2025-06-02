package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleDayCaregiverListResDto {

    private List<ScheduleDayCaregiverResDto> schedule;

    @Builder
    public ScheduleDayCaregiverListResDto(List<ScheduleDayCaregiverResDto> schedule) {
        this.schedule = schedule;
    }

    public static ScheduleDayCaregiverListResDto from(List<ScheduleDayCaregiverResDto> schedule) {
        return ScheduleDayCaregiverListResDto.builder()
                .schedule(schedule)
                .build();
    }

}
