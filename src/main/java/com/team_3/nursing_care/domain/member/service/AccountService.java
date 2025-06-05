package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Page<AccountListResponseDto> getAccountList(String searchName, String searchRole, CustomUserDetails userDetails, Pageable pageable);


}
