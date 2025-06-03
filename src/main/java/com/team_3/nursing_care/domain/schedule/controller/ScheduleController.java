package com.team_3.nursing_care.domain.schedule.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.dto.request.ReadScheduleDayCaregiverReqDto;
import com.team_3.nursing_care.domain.schedule.dto.request.ReadScheduleMonthCaregiverReqDto;
import com.team_3.nursing_care.domain.schedule.dto.request.UpdateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleDayCaregiverListResDto;
import com.team_3.nursing_care.domain.schedule.dto.response.ScheduleMonthCaregiverListResDto;
import com.team_3.nursing_care.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/care-giver/day/{caregiverId}")
    public ResponseEntity<ResponseDto<ScheduleDayCaregiverListResDto>> getScheduleDayList(@PathVariable Long caregiverId,
                                                                             @RequestBody ReadScheduleDayCaregiverReqDto readScheduleDayCaregiverReqDto){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleDayCaregiverList(caregiverId, readScheduleDayCaregiverReqDto)));
    }

    @GetMapping("/care-giver/month/{caregiverId}")
    public ResponseEntity<ResponseDto<ScheduleMonthCaregiverListResDto>> getScheduleMonthList(@PathVariable Long caregiverId,
                                                                                              @RequestBody ReadScheduleMonthCaregiverReqDto readScheduleMonthCaregiverReqDto){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, scheduleService.getScheduleMonthCaregiverList(caregiverId, readScheduleMonthCaregiverReqDto)));
    }

}
