package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.exception.MemberException;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateAccountRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateLoginIdRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateLoginPwRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.MemberNameResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdateAccountResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.member.repository.custom.MemberCustomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    @Override
    public List<MemberNameResponseDto> getMemberNameList(String role, CustomUserDetails userDetails) {

        Long companyId = userDetails.getCompanyId();

        Role realRole;
        try {
            realRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("유효하지 않은 권한입니다: " + role);
        }

        Sort sort = Sort.by("memberName").ascending();
        List<Member> members = memberRepository.findByCompany_CompanyIdAndRoleAndLoginIdIsNullAndIsDeletedFalse(companyId,realRole,sort);

        List<MemberNameResponseDto> dtoList = members.stream()
                .map(MemberNameResponseDto::from)
                .collect(Collectors.toList());

        return dtoList;
    }


    @Transactional(readOnly = true)
    @Override
    public UpdateAccountResponseDto getLoginId(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

        return UpdateAccountResponseDto.from(member);

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

        member.updateLoginId(dto.getLoginId());
        member.updateLoginPw(encodePw);

    }

    @Transactional
    @Override
    public void updateLoginId(Long memberId, UpdateLoginIdRequestDto updateLoginIdRequestDto) {
        Member member = memberRepository.findByLoginId(updateLoginIdRequestDto.getLoginNewId()).orElse(null);

        String loginNewId = updateLoginIdRequestDto.getLoginNewId();

        if (member != null)
            throw new MemberException(HttpStatusCode.BAD_REQUEST, "duplicate login id: " + loginNewId);

        member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

        member.updateLoginId(loginNewId);
    }

    @Transactional
    @Override
    public void updateLoginPw(Long memberId, UpdateLoginPwRequestDto updateLoginPwRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

        String loginNewPw = updateLoginPwRequestDto.getLoginNewPw();

        if (!loginNewPw.equals(updateLoginPwRequestDto.getLoginNewPwConfirm()))
            throw new MemberException(HttpStatusCode.BAD_REQUEST, "not match login password Confirm");

        String encodePw = passwordEncoder.encode(loginNewPw);

        member.updateLoginPw(encodePw);
    }

    @Transactional
    @Override
    public void deleteAccount(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

        member.updateAccountIsDeleted(true);
    }


}
