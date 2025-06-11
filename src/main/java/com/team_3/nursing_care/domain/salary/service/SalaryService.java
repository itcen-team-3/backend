package com.team_3.nursing_care.domain.salary.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.salary.dto.response.SalaryScheduleResDto;

import java.time.LocalDate;
import java.util.List;

public interface SalaryService {

    List<SalaryScheduleResDto> getSalarySchedule(int year, int month, CustomUserDetails userDetails);
}
