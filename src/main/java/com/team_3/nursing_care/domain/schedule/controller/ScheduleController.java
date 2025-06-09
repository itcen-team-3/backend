package com.team_3.nursing_care.domain.schedule.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.schedule.dto.request.*;
import com.team_3.nursing_care.domain.schedule.dto.response.*;
import com.team_3.nursing_care.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/work-schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<?> createWorkSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto){
        scheduleService.addWorkSchedule(createScheduleRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "근무 일정표가 정상적으로 생성 되었습니다."));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> deleteWorkSchedule(@PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "근무 일정표가 정상적으로 삭제 되었습니다."));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<?> updateWorkSchedule(@PathVariable Long scheduleId,
                                                @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto){
        scheduleService.editSchedule(scheduleId, updateScheduleRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "근무 일정표를 정상적으로 수정 하였습니다."));
    }

    @GetMapping("/care-giver/day/{scheduleDate}")
    public ResponseEntity<ResponseDto<ScheduleDayCaregiverListResDto>> getScheduleDayList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                                          @PathVariable LocalDate scheduleDate){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleDayCaregiverList(userDetails.getMemberId(), scheduleDate)));
    }

    @GetMapping("/care-giver/month/{yearMonth}")
    public ResponseEntity<ResponseDto<ScheduleMonthCaregiverListResDto>> getScheduleMonthList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                                              @PathVariable String yearMonth){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleMonthCaregiverList(userDetails.getMemberId(), yearMonth)));
    }

    @GetMapping("/care-giver/week/{startDate}")
    public ResponseEntity<ResponseDto<ScheduleWeekCaregiverListResDto>> getScheduleWeekList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                                            @PathVariable LocalDate startDate){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleWeekCaregiverList(userDetails.getMemberId(), startDate)));
    }

    @PostMapping("/admin/week")
    public ResponseEntity<ResponseDto<ScheduleWeekAdminListResDto>> getScheduleWeekAdminList(@RequestBody ReadScheduleWeekAdminReqDto readScheduleWeekAdminReqDto){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleWeekByAdmin(readScheduleWeekAdminReqDto)));
    }

    @GetMapping("/admin/day/{scheduleId}")
    public ResponseEntity<ResponseDto<ScheduleDayAdminResDto>> getScheduleDayByAdmin(@PathVariable Long scheduleId){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleDayByAdmin(scheduleId)));
    }


}
