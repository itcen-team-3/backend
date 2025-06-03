package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleMonthCaregiverResDto {

    private Long scheduleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int workDay;

    @Builder
    public ScheduleMonthCaregiverResDto(Long scheduleId,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        int workDay) {
        this.scheduleId = scheduleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDay = workDay;
    }
}
