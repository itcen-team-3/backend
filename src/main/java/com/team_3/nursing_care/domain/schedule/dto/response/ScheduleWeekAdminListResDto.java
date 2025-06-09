package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleWeekAdminListResDto {

    private List<ScheduleWeekAdminResDto> schedulesWeek;

    @Builder
    public ScheduleWeekAdminListResDto(List<ScheduleWeekAdminResDto> schedulesWeek) {
        this.schedulesWeek = schedulesWeek;
    }

    public static ScheduleWeekAdminListResDto from(List<ScheduleWeekAdminResDto> schedules) {
        return ScheduleWeekAdminListResDto.builder()
                .schedulesWeek(schedules)
                .build();
    }


}
