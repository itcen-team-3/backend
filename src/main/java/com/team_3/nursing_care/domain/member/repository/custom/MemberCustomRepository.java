package com.team_3.nursing_care.domain.member.repository.custom;

import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {
    Page<AccountListResponseDto> getAccountList(Long companyId, String searchName, String searchRole, Pageable pageable);
}
