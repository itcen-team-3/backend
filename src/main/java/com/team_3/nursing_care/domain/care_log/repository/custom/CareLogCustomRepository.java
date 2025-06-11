package com.team_3.nursing_care.domain.care_log.repository.custom;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CareLogCustomRepository {
    Page<ResCareLogDto> getCareLogPage(LocalDate date, Pageable pageable, CustomUserDetails userDetails, Company company);
}
