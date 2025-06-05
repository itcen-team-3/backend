package com.team_3.nursing_care.domain.attendance.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceAdminListResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverListResDto;
import com.team_3.nursing_care.domain.attendance.service.AttendanceExplationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/attendance-explation")
@RequiredArgsConstructor
@Slf4j
public class AttendanceExplationController {

    private final AttendanceExplationService attendanceExplationService;

    @PostMapping
    public ResponseEntity<?> createAttendanceExplation(@RequestBody CreateAttendanceExplationReqDto createAttendanceExplationReqDto) {
        attendanceExplationService.createAttendanceExplation(createAttendanceExplationReqDto);

        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "소명 작성이 완료 되었습니다."));
    }

    @GetMapping("/care-giver/{caregiverId}")
    public ResponseEntity<ResponseDto<AttendanceCaregiverListResDto>> getAttendanceExplations(@PathVariable Long caregiverId) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, attendanceExplationService.getAttendanceExplationList(caregiverId)));
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<ResponseDto<AttendanceAdminListResDto>> getAttendanceByAdmin(@PathVariable Long adminId){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, attendanceExplationService.getAttendanceExplationListByAdmin(adminId)));
    }

}
