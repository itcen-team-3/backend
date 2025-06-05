package com.team_3.nursing_care.domain.care_log.repository.custom;

import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CareLogCustomRepository {
    Page<ResCareLogDto> getCareLogPage(LocalDate date, Pageable pageable, Long memberId);
}
