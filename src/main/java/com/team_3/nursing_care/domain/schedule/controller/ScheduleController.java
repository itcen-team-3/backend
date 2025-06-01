package com.team_3.nursing_care.domain.schedule.controller;

import com.team_3.nursing_care.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/work-schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<?> createWorkSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto){
        scheduleService.addWorkSchedule(createScheduleRequestDto);
        return ResponseEntity.ok("근무 일정표가 정상적으로 생성 되었습니다.");
    }
}
