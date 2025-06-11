package com.team_3.nursing_care.domain.salary.controller;

import com.team_3.nursing_care.common.exception.ResultMessage;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.salary.entity.Salary;
import com.team_3.nursing_care.domain.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.http.HttpStatusCode;

import java.time.LocalDate;

import static com.team_3.nursing_care.common.exception.ResultMessage.*;
import static software.amazon.awssdk.http.HttpStatusCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salary")
public class SalaryController {

    private final SalaryService salaryService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> findSalarySchedule(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, salaryService.getSalarySchedule(year, month, userDetails)));
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @GetMapping("/{id}")
//    private Salary findById(
//            @PathVariable Long id,
//            @AuthenticationPrincipal CustomUserDetails userDetails
//    ) {
//        salaryService.getSalaryScheduleById(id, userDetails);
//        return null;
//    }

}
