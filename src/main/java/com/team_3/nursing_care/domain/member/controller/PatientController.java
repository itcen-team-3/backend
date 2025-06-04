package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdatePatientResponseDto;
import com.team_3.nursing_care.domain.member.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<UpdatePatientResponseDto>> getPatientInfo(@PathVariable("patientId") Long patientId) {

        UpdatePatientResponseDto patient = patientService.getPatientInfo(patientId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, patient));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createPatient(@Validated @ModelAttribute CreatePatientRequestDto createPatientRequestDto, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        patientService.addPatient(createPatientRequestDto, Role.PATIENT, profileImage);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "새로운 보호대상자가 정상적으로 등록 되었습니다."));
    }

    @PutMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCaregiver(@PathVariable("patientId") Long patientId,
                                             @Validated @ModelAttribute UpdatePatientRequestDto updatePatientRequestDto,
                                             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        patientService.updatePatient(patientId, updatePatientRequestDto, profileImage);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "보호대상자 정보가 정상적으로 수정되었습니다."));
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePatient(@PathVariable("patientId") Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "보호대상자 정보가 정상적으로 삭제되었습니다."));
    }

}
