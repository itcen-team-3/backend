package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.response.PageResponseDto;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverDetailResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdateCaregiverResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.service.CaregiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/member/caregiver")
@RequiredArgsConstructor
public class CaregiverController {

    private final CaregiverService caregiverService;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDto<CaregiverListResponseDto>> getCaregiverList(@RequestParam(name = "companyId") Long companyId,
                                                                                      @RequestParam(name = "searchName", required = false) String searchName,
                                                                                      @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CaregiverListResponseDto> caregiverPage = caregiverService.getCaregiverList(companyId, searchName, pageable);
        return ResponseEntity.ok(new PageResponseDto<>(caregiverPage));

    }

    @GetMapping("/detail/{caregiverId}")
    public ResponseEntity<ResponseDto<CaregiverDetailResponseDto>> getCaregiverDetail(@PathVariable("caregiverId") Long caregiverId) {

        CaregiverDetailResponseDto caregiver = caregiverService.getCaregiverDetail(caregiverId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, caregiver));

    }

    @GetMapping("/{caregiverId}")
    public ResponseEntity<ResponseDto<UpdateCaregiverResponseDto>> getCaregiverInfo(@PathVariable("caregiverId") Long caregiverId) {

        UpdateCaregiverResponseDto caregiver = caregiverService.getCaregiverInfo(caregiverId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, caregiver));
    }

    @PostMapping
    public ResponseEntity<?> createCaregiver(@Validated @ModelAttribute CreateCaregiverRequestDto dto, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage, @AuthenticationPrincipal Member loginAdmin) {

        caregiverService.addCaregiver(dto, Role.CAREGIVER, profileImage, loginAdmin);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "새로운 요양보호사가 정상적으로 등록 되었습니다."));
    }

    @PutMapping("/{caregiverId}")
    public ResponseEntity<?> updateCaregiver(@PathVariable("caregiverId") Long caregiverId,
                                             @Validated @ModelAttribute UpdateCaregiverRequestDto updateCaregiverRequestDto,
                                             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        caregiverService.updateCaregiver(caregiverId, updateCaregiverRequestDto, profileImage);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "요양보호사 정보가 정상적으로 수정되었습니다."));
    }

}
