package com.team_3.nursing_care.domain.schedule.service;

import com.team_3.nursing_care.domain.schedule.dto.request.CreateScheduleRequestDto;

public interface ScheduleService {

    void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    void deleteSchedule(Long scheduleId);

}
