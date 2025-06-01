package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.domain.member.dto.request.CaregiverInfoRequestDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.exception.CompanyNotFoundException;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
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
public class CaregiverServiceImpl implements CaregiverService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Service s3Service;
    private final PasswordEncoder passwordEncoder;

    public List<Member> getMembers(Long companyId, Role role) {
        return memberRepository.findByCompany_CompanyIdAndRole(companyId, role);
    }

    public Member addCaregiver(CaregiverInfoRequestDto caregiverInfoRequestDto, Role role){

        Company company = companyRepository.findById(caregiverInfoRequestDto.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("아이디가 " + caregiverInfoRequestDto.getCompanyId()+"인 시설이 존재하지 않습니다."));

        String profileImageUrl = s3Service.uploadProfileFile(caregiverInfoRequestDto.getProfileImage());

        Member member = caregiverInfoRequestDto.toEntity(profileImageUrl,company,role);

        return memberRepository.save(member);
    }

}
