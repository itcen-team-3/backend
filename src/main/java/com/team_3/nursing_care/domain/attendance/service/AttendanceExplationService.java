package com.team_3.nursing_care.domain.attendance.service;

import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.dto.request.UpdateApprovementTypeReqDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceAdminListResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverListResDto;

public interface AttendanceExplationService {

    void createAttendanceExplation(Long caregiverId, CreateAttendanceExplationReqDto createAttendanceExplationReqDto);
    AttendanceCaregiverListResDto getAttendanceExplationList(Long caregiverId);
    AttendanceAdminListResDto getAttendanceExplationListByAdmin(Long adminId);
    void updateAttendanceApprovementType(Long attendanceExplationId, UpdateApprovementTypeReqDto updateApprovementTypeReqDto);

}
