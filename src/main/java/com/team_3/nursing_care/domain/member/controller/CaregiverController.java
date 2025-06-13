package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.response.PageResponseDto;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverDashboardResDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/member/caregiver")
@RequiredArgsConstructor
public class CaregiverController {

    private final CaregiverService caregiverService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getCaregiverList(@RequestParam(name = "searchName", required = false) String searchName,
                                                                                      @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                                      @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CaregiverListResponseDto> caregiverPage = caregiverService.getCaregiverList(searchName, userDetails, pageable);
        return ResponseEntity.ok(new ResponseDto<>(OK,Success,new PageResponseDto<>(caregiverPage)));

    }

    @GetMapping("/detail/{caregiverId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDto<CaregiverDetailResponseDto>> getCaregiverDetail(@PathVariable("caregiverId") Long caregiverId) {

        CaregiverDetailResponseDto caregiver = caregiverService.getCaregiverDetail(caregiverId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, caregiver));

    }

    @GetMapping("/{caregiverId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDto<UpdateCaregiverResponseDto>> getCaregiverInfo(@PathVariable("caregiverId") Long caregiverId) {

        UpdateCaregiverResponseDto caregiver = caregiverService.getCaregiverInfo(caregiverId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, caregiver));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createCaregiver(@Validated @ModelAttribute CreateCaregiverRequestDto dto, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage, @AuthenticationPrincipal CustomUserDetails userDetails) {

        caregiverService.addCaregiver(dto, Role.CAREGIVER, profileImage, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "새로운 요양보호사가 정상적으로 등록 되었습니다."));
    }

    @PutMapping("/{caregiverId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateCaregiver(@PathVariable("caregiverId") Long caregiverId,
                                             @Validated @ModelAttribute UpdateCaregiverRequestDto updateCaregiverRequestDto,
                                             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        caregiverService.updateCaregiver(caregiverId, updateCaregiverRequestDto, profileImage);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "요양보호사 정보가 정상적으로 수정되었습니다."));
    }

    @DeleteMapping("/{caregiverId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteCaregiver(@PathVariable("caregiverId") Long caregiverId) {
        caregiverService.deleteCaregiver(caregiverId);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "요양보호사 정보가 정상적으로 삭제되었습니다."));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseDto<CaregiverDashboardResDto>> getCaregiverDashBoard(
                                                                                        @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, caregiverService.getCaregiverDashBoard(userDetails.getMemberId())));
    }

}
