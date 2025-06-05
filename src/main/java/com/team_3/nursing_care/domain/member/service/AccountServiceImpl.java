package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.member.repository.custom.MemberCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<AccountListResponseDto> getAccountList(String searchName, String searchRole, CustomUserDetails userDetails, Pageable pageable) {

        Long companyId = userDetails.getCompanyId();

        return memberCustomRepository.getAccountList(companyId, searchName, searchRole, pageable);

    }
}
