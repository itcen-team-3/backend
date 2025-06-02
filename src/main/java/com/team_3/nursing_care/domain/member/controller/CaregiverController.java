package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CaregiverInfoRequestDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.service.CaregiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class CaregiverController {

    private final CaregiverService caregiverService;

    @PostMapping("/caregiver")
    public ResponseEntity<?> createCaregiver(@Validated @ModelAttribute CaregiverInfoRequestDto dto, @RequestPart(value="profileImage", required = false) MultipartFile profileImage, @AuthenticationPrincipal Member loginAdmin) {

        caregiverService.addCaregiver(dto, Role.CAREGIVER, profileImage, loginAdmin);
        return ResponseEntity.ok("새로운 요양보호사가 정상적으로 등록 되었습니다.");
    }

}
