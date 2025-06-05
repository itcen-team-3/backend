package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientDetailResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdatePatientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PatientService {

    Page<PatientListResponseDto> getPatientList(String searchName, CustomUserDetails userDetails,Pageable pageable);

    PatientDetailResponseDto getPatientDetail(Long patientId);

    UpdatePatientResponseDto getPatientInfo(Long patientId);

    void addPatient(CreatePatientRequestDto createPatientRequestDto, Role role, MultipartFile profileImage, CustomUserDetails userDetails);

    void updatePatient(Long patientId, UpdatePatientRequestDto updatePatientRequestDto, MultipartFile profileImage);

    void deletePatient(Long patientId);
}
