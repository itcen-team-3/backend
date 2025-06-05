package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.exception.MemberException;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.dto.request.CreateAccountRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.member.repository.custom.MemberCustomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public Page<AccountListResponseDto> getAccountList(String searchName, String searchRole, CustomUserDetails userDetails, Pageable pageable) {

        Long companyId = userDetails.getCompanyId();

        return memberCustomRepository.getAccountList(companyId, searchName, searchRole, pageable);

    }

    @Transactional
    @Override
    public void addAccount(Long memberId, CreateAccountRequestDto dto) {

        Member member = memberRepository.findByLoginId(dto.getLoginId()).orElse(null);
        if (member != null)
            throw new MemberException(HttpStatusCode.BAD_REQUEST, "duplicate login id: " + dto.getLoginId());

        member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

        String encodePw = passwordEncoder.encode(dto.getLoginPw());

        member.updateAccount(dto.getLoginId(), encodePw);

    }


}
