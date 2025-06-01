package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.domain.member.dto.request.MemberInfoRequestDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Service s3Service;
    private final PasswordEncoder passwordEncoder;

    public List<Member> getMembers(Long companyId, Role role) {
        return memberRepository.findByCompany_CompanyIdAndRole(companyId, role);
    }

    public Member addMember(MemberInfoRequestDto caregiverInfoRequestDto, Role role){

        Company company = companyRepository.findById(caregiverInfoRequestDto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("해당 시설이 존재하지 않습니다."));

        String profileImageUrl = s3Service.uploadProfileFile(caregiverInfoRequestDto.getProfileImage());

        Member member = caregiverInfoRequestDto.toEntity(profileImageUrl,company);

        return memberRepository.save(member);
    }

}
