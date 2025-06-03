package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleMonthCaregiverListResDto {

    List<ScheduleMonthCaregiverResDto> scheduleMonth;

    @Builder
    public ScheduleMonthCaregiverListResDto(List<ScheduleMonthCaregiverResDto> scheduleMonth) {
        this.scheduleMonth = scheduleMonth;
    }

    public static ScheduleMonthCaregiverListResDto from(List<ScheduleMonthCaregiverResDto> scheduleMonth) {
        return ScheduleMonthCaregiverListResDto.builder()
                .scheduleMonth(scheduleMonth)
                .build();
    }
}
