package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.response.PageResponseDto;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdatePatientRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientDetailResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdatePatientResponseDto;
import com.team_3.nursing_care.domain.member.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getPatientList(@RequestParam(name = "searchName", required = false) String searchName,
                                            @AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PatientListResponseDto> patientPage = patientService.getPatientList(searchName, userDetails,pageable);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, new PageResponseDto<>(patientPage)));

    }

    @GetMapping("/detail/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDto<PatientDetailResponseDto>> getPatientDetail(@PathVariable("patientId") Long patientId) {

        PatientDetailResponseDto patient = patientService.getPatientDetail(patientId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, patient));

    }

    @GetMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDto<UpdatePatientResponseDto>> getPatientInfo(@PathVariable("patientId") Long patientId) {

        UpdatePatientResponseDto patient = patientService.getPatientInfo(patientId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, patient));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity createPatient(@Validated @ModelAttribute CreatePatientRequestDto createPatientRequestDto, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage, @AuthenticationPrincipal CustomUserDetails userDetails) {
        patientService.addPatient(createPatientRequestDto, Role.PATIENT, profileImage, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "새로운 보호대상자가 정상적으로 등록 되었습니다."));
    }

    @PutMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateCaregiver(@PathVariable("patientId") Long patientId,
                                             @Validated @ModelAttribute UpdatePatientRequestDto updatePatientRequestDto,
                                             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        patientService.updatePatient(patientId, updatePatientRequestDto, profileImage);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "보호대상자 정보가 정상적으로 수정되었습니다."));
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deletePatient(@PathVariable("patientId") Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "보호대상자 정보가 정상적으로 삭제되었습니다."));
    }

}
