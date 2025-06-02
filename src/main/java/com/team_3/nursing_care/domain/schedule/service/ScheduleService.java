package com.team_3.nursing_care.domain.schedule.service;

import com.team_3.nursing_care.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.dto.request.UpdateScheduleRequestDto;

public interface ScheduleService {

    void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    void deleteSchedule(Long scheduleId);
    void editSchedule(Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto);

}
