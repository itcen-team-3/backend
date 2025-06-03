package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleWeekCaregiverListDto {

    List<ScheduleWeekCaregiverResDto> scheduleWeek;

    @Builder
    public ScheduleWeekCaregiverListDto(List<ScheduleWeekCaregiverResDto> scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }

    public static ScheduleWeekCaregiverListDto from(List<ScheduleWeekCaregiverResDto> scheduleWeek){
        return ScheduleWeekCaregiverListDto.builder()
                .scheduleWeek(scheduleWeek)
                .build();
    }

}
