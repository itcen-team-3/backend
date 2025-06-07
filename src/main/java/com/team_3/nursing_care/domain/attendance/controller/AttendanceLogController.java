package com.team_3.nursing_care.domain.attendance.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceDayResponseListDto;
import com.team_3.nursing_care.domain.attendance.service.AttendanceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/attendance-log")
@RequiredArgsConstructor
public class AttendanceLogController {

    private final AttendanceLogService attendanceLogService;

    @GetMapping("/care-giver/date")
    public ResponseEntity<ResponseDto<AttendanceDayResponseListDto>> getAttendanceLog(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, attendanceLogService.getDeAttendanceDay(userDetails.getMemberId())));
    }


}
