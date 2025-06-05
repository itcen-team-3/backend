package com.team_3.nursing_care.domain.care_log.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareLogDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareLogDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDetailDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CareLogService {

    void postCareLog(ReqCreateCareLogDto reqCareLogDto, CustomUserDetails userDetails);

    Page<ResCareLogDto> getCareLogPage(LocalDate date, Pageable pageable, CustomUserDetails userDetails);

    ResCareLogDetailDto getCareLogById(Long id, CustomUserDetails userDetails);

    void deleteCareLogById(Long id, CustomUserDetails userDetails);

    void updateCareLogById(Long id, ReqUpdateCareLogDto reqUpdateCareLogDto, CustomUserDetails userDetails);

}
