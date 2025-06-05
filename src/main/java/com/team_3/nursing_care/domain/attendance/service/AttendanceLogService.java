package com.team_3.nursing_care.domain.attendance.service;

import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceDayResponseListDto;

public interface AttendanceLogService {

    AttendanceDayResponseListDto getDeAttendanceDay(Long caregiverId);

}
