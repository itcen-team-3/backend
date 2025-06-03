package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import com.team_3.nursing_care.domain.member.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/member/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity createPatient(@Validated @ModelAttribute CreatePatientRequestDto createPatientRequestDto, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        patientService.addPatient(createPatientRequestDto, Role.PATIENT, profileImage);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "새로운 보호대상자가 정상적으로 등록 되었습니다."));
    }

}
