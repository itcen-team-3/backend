package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CaregiverInfoRequestDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CaregiverServiceImpl implements CaregiverService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Service s3Service;
    private final PasswordEncoder passwordEncoder;

    public List<Member> getMembers(Long companyId, Role role) {
        return memberRepository.findByCompany_CompanyIdAndRole(companyId, role);
    }

    public Member addCaregiver(CaregiverInfoRequestDto caregiverInfoRequestDto, Role role, MultipartFile profileImage, Member admin) {

        String key;

        Company company = admin.getCompany();

        if(profileImage != null && !profileImage.isEmpty()){
            key = s3Service.uploadProfileFile(profileImage);
        }else{
            key = "profile/profileImage.png";
        }
        String profileImageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;

        Member caregiver = caregiverInfoRequestDto.toEntity(profileImageUrl, company, role, admin);

        return memberRepository.save(caregiver);
    }

}
