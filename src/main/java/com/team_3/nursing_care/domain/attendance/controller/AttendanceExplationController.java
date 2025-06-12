package com.team_3.nursing_care.domain.attendance.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.attendance.dto.request.CreateAttendanceExplationReqDto;
import com.team_3.nursing_care.domain.attendance.dto.request.UpdateApprovementTypeReqDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceAdminListResDto;
import com.team_3.nursing_care.domain.attendance.dto.response.AttendanceCaregiverListResDto;
import com.team_3.nursing_care.domain.attendance.service.AttendanceExplationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> createAttendanceExplation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @RequestBody CreateAttendanceExplationReqDto createAttendanceExplationReqDto) {
        attendanceExplationService.createAttendanceExplation(userDetails.getMemberId(), createAttendanceExplationReqDto);

        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "소명 작성이 완료 되었습니다."));
    }

    @GetMapping("/care-giver")
    public ResponseEntity<ResponseDto<AttendanceCaregiverListResDto>> getAttendanceExplations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, attendanceExplationService.getAttendanceExplationList(userDetails.getMemberId())));
    }

    @GetMapping("/admin")
    public ResponseEntity<ResponseDto<AttendanceAdminListResDto>> getAttendanceByAdmin(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, attendanceExplationService.getAttendanceExplationListByAdmin(userDetails.getMemberId())));
    }

    @PutMapping("/admin/{attendanceExplationId}")
    public ResponseEntity<?> updateApprovementType(@PathVariable Long attendanceExplationId,
                                                   @RequestBody UpdateApprovementTypeReqDto approvementTypeReqDto){
        attendanceExplationService.updateAttendanceApprovementType(attendanceExplationId, approvementTypeReqDto);
        return ResponseEntity.ok(new ResponseDto<>(OK,Success, "승인 여부 변경이 완료 되었습니다."));
    }
    
}
