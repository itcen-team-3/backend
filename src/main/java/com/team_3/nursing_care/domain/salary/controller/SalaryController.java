package com.team_3.nursing_care.domain.salary.controller;

import com.team_3.nursing_care.domain.salary.entity.Salary;
import com.team_3.nursing_care.domain.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/{id}")
    private Salary findById(@PathVariable Long id) {
        return null;
    }
}
