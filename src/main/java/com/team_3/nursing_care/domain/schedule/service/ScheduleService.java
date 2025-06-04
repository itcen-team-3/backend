package com.team_3.nursing_care.domain.schedule.service;

import com.team_3.nursing_care.domain.schedule.dto.request.*;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleDayCaregiverListResDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleMonthCaregiverListResDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleWeekAdminListResDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleWeekCaregiverListResDto;

public interface ScheduleService {

    void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    void deleteSchedule(Long scheduleId);
    void editSchedule(Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto);
    ScheduleDayCaregiverListResDto getScheduleDayCaregiverList(Long caregiverId, ReadScheduleDayCaregiverReqDto readScheduleDayCaregiverReqDto);
    ScheduleMonthCaregiverListResDto getScheduleMonthCaregiverList(Long caregiverId, ReadScheduleMonthCaregiverReqDto readScheduleMonthCaregiverReqDto);
    ScheduleWeekCaregiverListResDto getScheduleWeekCaregiverList(Long caregiverId, ReadScheduleWeekCaregiverReqDto readScheduleWeekCaregiverReqDto);
    ScheduleWeekAdminListResDto getScheduleWeekByAdmin(ReadScheduleWeekAdminReqDto readScheduleWeekAdminReqDto);

}