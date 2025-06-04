package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverDetailResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdateCaregiverResponseDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaregiverServiceImpl implements CaregiverService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public Page<CaregiverListResponseDto> getCaregiverList(Long companyId, String searchName, Pageable pageable) {

        Page<Member> caregivers;

        if (searchName != null && !searchName.isBlank()) {
            caregivers = memberRepository.findByCompany_CompanyIdAndRoleAndMemberNameContainingAndIsDeletedFalse(companyId, Role.CAREGIVER, searchName, pageable);
        } else {
            caregivers = memberRepository.findByCompany_CompanyIdAndRoleAndIsDeletedFalse(companyId, Role.CAREGIVER, pageable);
        }

        return caregivers.map(CaregiverListResponseDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public CaregiverDetailResponseDto getCaregiverDetail(Long caregiverId) {

        Member member = memberRepository.findByMemberIdAndRole(caregiverId, Role.CAREGIVER)
                .orElseThrow(() -> new EntityNotFoundException("해당 요양보호사를 찾을 수 없습니다."));

        int age = calculateAge(member.getBirthDate());

        List<Schedule> schedules = scheduleRepository.findAllByMember_MemberIdAndIsDeletedFalse(caregiverId);

        return CaregiverDetailResponseDto.from(member, schedules, age);
    }

    @Transactional(readOnly = true)
    @Override
    public UpdateCaregiverResponseDto getCaregiverInfo(Long caregiverId) {
        Member member = memberRepository.findByMemberIdAndRole(caregiverId, Role.CAREGIVER)
                .orElseThrow(() -> new EntityNotFoundException("해당 요양보호사를 찾을 수 없습니다."));

        return UpdateCaregiverResponseDto.from(member);
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

    @Transactional
    @Override
    public void updateCaregiver(Long caregiverId, UpdateCaregiverRequestDto updateCaregiverRequestDto, MultipartFile profileImage) {

        String key;
        String profileImageUrl;

        Member caregiver = memberRepository.findByMemberIdAndRole(caregiverId, Role.CAREGIVER)
                .orElseThrow(() -> new EntityNotFoundException("해당 요양보호사를 찾을 수 없습니다."));

        if (profileImage != null && !profileImage.isEmpty()) {
            key = s3Service.uploadProfileFile(profileImage);
            profileImageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
        } else {
            profileImageUrl = memberRepository.findByMemberIdAndRole(caregiverId, Role.CAREGIVER).get().getProfileImageUrl();
        }

        caregiver.updateCaregiver(updateCaregiverRequestDto, profileImageUrl);

    }

    @Transactional
    @Override
    public void deleteCaregiver(Long caregiverId) {
        Member caregiver = memberRepository.findByMemberIdAndRole(caregiverId, Role.CAREGIVER)
                .orElseThrow(() -> new IllegalStateException("해당 요양보호사를 찾을 수 없습니다."));

        caregiver.updateIsDeleted(true);
    }

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birthDate must not be null");
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
