package com.team_3.nursing_care.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleMonthCaregiverResDto {

    private Long scheduleId;
    private LocalDate date;

    @Builder
    public ScheduleMonthCaregiverResDto(Long scheduleId, LocalDate date) {
        this.scheduleId = scheduleId;
        this.date=date;
    }
}
