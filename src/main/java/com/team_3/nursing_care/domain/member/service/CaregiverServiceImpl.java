package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverListResponseDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CaregiverServiceImpl implements CaregiverService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Service s3Service;

    @Override
    public Page<CaregiverListResponseDto> getCaregiverList(Long companyId, String searchName, Pageable pageable) {

        Page<Member> caregivers;

        if (searchName != null && !searchName.isBlank()) {
            caregivers = memberRepository.findByCompany_CompanyIdAndRoleAndMemberNameContaining(companyId, Role.CAREGIVER, searchName, pageable);
        } else {
            caregivers = memberRepository.findByCompany_CompanyIdAndRole(companyId, Role.CAREGIVER, pageable);
        }

        return caregivers.map(CaregiverListResponseDto::from);
    }

    @Transactional
    @Override
    public Member addCaregiver(CreateCaregiverRequestDto createCaregiverRequestDto, Role role, MultipartFile profileImage, Member admin) {

        String key;

        Company company = admin.getCompany();

        if (profileImage != null && !profileImage.isEmpty()) {
            key = s3Service.uploadProfileFile(profileImage);
        } else {
            key = "profile/profileImage.png";
        }
        String profileImageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;

        Member caregiver = createCaregiverRequestDto.toEntity(profileImageUrl, company, role, admin);

        return memberRepository.save(caregiver);
    }

}
