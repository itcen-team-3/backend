package com.team_3.nursing_care.domain.salary.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.salary.dto.response.SalaryScheduleResDto;
import com.team_3.nursing_care.domain.salary.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;


    @Override
    public SalaryScheduleResDto getSalarySchedule(CustomUserDetails userDetails) {


        return null;
    }
}
