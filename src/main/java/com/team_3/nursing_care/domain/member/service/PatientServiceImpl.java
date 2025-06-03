package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Service s3Service;

    @Transactional
    @Override
    public void addPatient(CreatePatientRequestDto createPatientRequestDto, Role role, MultipartFile profileImage) {

        String key;

        Company company = companyRepository.findById(createPatientRequestDto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("해당 시설을 찾을 수 없습니다."));

        if (profileImage != null && !profileImage.isEmpty()) {
            key = s3Service.uploadProfileFile(profileImage);
        } else {
            key = "profile/profileImage.png";
        }
        String profileImageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;

        Member patient = createPatientRequestDto.toMember(profileImageUrl, company, role);

        PatientInfo patientInfo = createPatientRequestDto.toPatient();

        patient.setPatientInfo(patientInfo); // 양방향 연결

        memberRepository.save(patient);

    }
}
