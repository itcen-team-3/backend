package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.dto.request.CreateAccountRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateLoginIdRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateLoginPwRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdateAccountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Page<AccountListResponseDto> getAccountList(String searchName, String searchRole, CustomUserDetails userDetails, Pageable pageable);

    UpdateAccountResponseDto getLoginId(Long memberId);

    void addAccount(Long memberId, CreateAccountRequestDto dto);

    void updateLoginId(Long memberId, UpdateLoginIdRequestDto updateLoginIdRequestDto);

    void updateLoginPw(Long memberId, UpdateLoginPwRequestDto updateLoginPwRequestDto);
}
