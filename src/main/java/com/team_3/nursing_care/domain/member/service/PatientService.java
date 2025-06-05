package com.team_3.nursing_care.domain.member.service;

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

    Page<PatientListResponseDto> getPatientList(Long companyId, String searchName, Pageable pageable);

    PatientDetailResponseDto getPatientDetail(Long patientId);

    UpdatePatientResponseDto getPatientInfo(Long patientId);

    void addPatient(CreatePatientRequestDto createPatientRequestDto, Role role, MultipartFile profileImage);

    void updatePatient(Long patientId, UpdatePatientRequestDto updatePatientRequestDto, MultipartFile profileImage);

    void deletePatient(Long patientId);
}
