package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientDetailResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdatePatientResponseDto;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.member.repository.PatientInfoRepository;
import com.team_3.nursing_care.domain.schedule.dto.response.PatientScheduleResponseDto;
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
import java.util.Map;
import java.util.stream.Collectors;

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
    private final PatientInfoRepository patientInfoRepository;
    private final ScheduleRepository scheduleRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public Page<PatientListResponseDto> getPatientList(String searchName, CustomUserDetails userDetails,Pageable pageable) {

        Page<Member> patients;

        Long companyId = userDetails.getCompanyId();

        if (searchName != null && !searchName.isBlank()) {
            patients = memberRepository.findByCompany_CompanyIdAndRoleAndMemberNameContainingAndIsDeletedFalse(companyId, Role.PATIENT, searchName, pageable);
        } else {
            patients = memberRepository.findByCompany_CompanyIdAndRoleAndIsDeletedFalse(companyId, Role.PATIENT, pageable);
        }

        return patients.map(PatientListResponseDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public PatientDetailResponseDto getPatientDetail(Long patientId) {

        Member patient = memberRepository.findByMemberIdAndRole(patientId, Role.PATIENT)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        PatientInfo patientInfo = patientInfoRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        List<Schedule> schedules = scheduleRepository.findAllByPatientIdAndIsDeletedFalse(patientId);

        List<Long> caregiverIds = schedules.stream()
                .map(schedule -> schedule.getMember().getMemberId())
                .distinct()
                .collect(Collectors.toList());

        List<Member> caregivers = memberRepository.findAllById(caregiverIds);

        Map<Long, String> caregiverNameMap = caregivers.stream()
                .collect(Collectors.toMap(Member::getMemberId, Member::getMemberName));

        List<PatientScheduleResponseDto> scheduleDtos = schedules.stream()
                .map(schedule -> {
                    String caregiverName = caregiverNameMap.get(schedule.getMember().getMemberId());
                    return PatientScheduleResponseDto.from(schedule, caregiverName);
                })
                .collect(Collectors.toList());

        int age = calculateAge(patient.getBirthDate());

        return PatientDetailResponseDto.from(patient, patientInfo, scheduleDtos, age);
    }

    @Transactional(readOnly = true)
    @Override
    public UpdatePatientResponseDto getPatientInfo(Long patientId) {
        Member patient = memberRepository.findByMemberIdAndRole(patientId, Role.PATIENT)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        PatientInfo patientInfo = patientInfoRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        return UpdatePatientResponseDto.from(patient, patientInfo);
    }

    @Transactional
    @Override
    public void addPatient(CreatePatientRequestDto createPatientRequestDto, Role role, MultipartFile profileImage, CustomUserDetails userDetails) {

        String key;

        Company company = companyRepository.findById(userDetails.getCompanyId())
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

    @Transactional
    @Override
    public void updatePatient(Long patientId, UpdatePatientRequestDto updatePatientRequestDto, MultipartFile profileImage) {

        String key;
        String profileImageUrl;

        Member patient = memberRepository.findByMemberIdAndRole(patientId, Role.PATIENT)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        PatientInfo patientInfo = patientInfoRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        List<Schedule> schedules = scheduleRepository.findAllByPatientIdAndIsDeletedFalse(patientId);

        schedules.forEach(schedule -> {
            schedule.updatePatientInfo(updatePatientRequestDto.getName(), updatePatientRequestDto.getAddress());
        });


        if (profileImage != null && !profileImage.isEmpty()) {
            key = s3Service.uploadProfileFile(profileImage);
            profileImageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
        } else {
            profileImageUrl = memberRepository.findByMemberIdAndRole(patientId, Role.PATIENT).get().getProfileImageUrl();
        }

        patient.updatePatient(updatePatientRequestDto, profileImageUrl);

        patientInfo.updatePatientInfo(updatePatientRequestDto);


    }

    @Transactional
    @Override
    public void deletePatient(Long patientId) {
        Member patient = memberRepository.findByMemberIdAndRole(patientId, Role.PATIENT)
                .orElseThrow(() -> new IllegalStateException("해당 보호대상자를 찾을 수 없습니다."));

        PatientInfo patientInfo = patientInfoRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호대상자를 찾을 수 없습니다."));

        patient.updateIsDeleted(true);

        patientInfo.updateIsDeleted(true);
    }

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birthDate must not be null");
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
