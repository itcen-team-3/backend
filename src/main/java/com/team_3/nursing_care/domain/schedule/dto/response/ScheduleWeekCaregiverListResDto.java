package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleWeekCaregiverListResDto {

    List<ScheduleWeekCaregiverResDto> scheduleWeek;

    @Builder
    public ScheduleWeekCaregiverListResDto(List<ScheduleWeekCaregiverResDto> scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }

    public static ScheduleWeekCaregiverListResDto from(List<ScheduleWeekCaregiverResDto> scheduleWeek){
        return ScheduleWeekCaregiverListResDto.builder()
                .scheduleWeek(scheduleWeek)
                .build();
    }

}
