package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface PatientService {

    void addPatient(CreatePatientRequestDto createPatientRequestDto, Role role, MultipartFile profileImage);

}
