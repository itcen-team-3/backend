package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.exception.ResultMessage;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.response.CaregiversNameListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientsNameListResponseDto;
import com.team_3.nursing_care.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/caregiver-name-list/{companyId}")
    public ResponseEntity<ResponseDto<CaregiversNameListResponseDto>> getCaregiverNameList(@PathVariable Long companyId) {
        return ResponseEntity.ok(new ResponseDto<>(OK, ResultMessage.Success, memberService.getCaregiversName(companyId, Role.CAREGIVER)));
    }

    @GetMapping("/patient-name-list/{companyId}")
    public ResponseEntity<ResponseDto<PatientsNameListResponseDto>> getPatientNameList(@PathVariable Long companyId) {
        return ResponseEntity.ok((new ResponseDto<>(OK, ResultMessage.Success, memberService.getPatientsName(companyId, Role.PATIENT))));
    }

    @GetMapping("/role-name-list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getRoleNameList() {
        return ResponseEntity.ok((new ResponseDto<>(OK, ResultMessage.Success, memberService.getRoleName())));
    }

}
