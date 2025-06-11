package com.team_3.nursing_care.domain.schedule.service;

import com.team_3.nursing_care.domain.schedule.dto.request.*;
import com.team_3.nursing_care.domain.schedule.dto.response.*;

import java.time.LocalDate;

public interface ScheduleService {

    void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    void deleteSchedule(Long scheduleId);
    void editSchedule(Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto);
    ScheduleDayCaregiverListResDto getScheduleDayCaregiverList(Long caregiverId, LocalDate scheduleDate);
    ScheduleMonthCaregiverListResDto getScheduleMonthCaregiverList(Long caregiverId, String yearMonth);
    ScheduleWeekCaregiverListResDto getScheduleWeekCaregiverList(Long caregiverId, LocalDate startDate);
    ScheduleWeekAdminListResDto getScheduleWeekByAdmin(ReadScheduleWeekAdminReqDto readScheduleWeekAdminReqDto);
    ScheduleDayAdminResDto getScheduleDayByAdmin(Long scheduleId);
    ScheduleReadResDto readSchedule(Long scheduleId);
}