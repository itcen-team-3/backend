package com.team_3.nursing_care.domain.salary.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.salary.dto.response.SalaryScheduleResDto;

public interface SalaryService {

    SalaryScheduleResDto getSalarySchedule(CustomUserDetails userDetails);
}
