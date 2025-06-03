package com.team_3.nursing_care.domain.schedule.service;

import com.team_3.nursing_care.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.dto.request.ReadScheduleDayCaregiverReqDto;
import com.team_3.nursing_care.domain.schedule.dto.request.ReadScheduleMonthCaregiverReqDto;
import com.team_3.nursing_care.domain.schedule.dto.request.UpdateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleDayCaregiverListResDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleMonthCaregiverListResDto;

public interface ScheduleService {

    void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    void deleteSchedule(Long scheduleId);
    void editSchedule(Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto);
    ScheduleDayCaregiverListResDto getScheduleDayCaregiverList(Long caregiverId, ReadScheduleDayCaregiverReqDto readScheduleDayCaregiverReqDto);
    ScheduleMonthCaregiverListResDto getScheduleMonthCaregiverList(Long caregiverId, ReadScheduleMonthCaregiverReqDto readScheduleMonthCaregiverReqDto);
}