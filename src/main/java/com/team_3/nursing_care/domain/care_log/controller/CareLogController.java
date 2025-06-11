package com.team_3.nursing_care.domain.care_log.controller;

import com.team_3.nursing_care.common.response.PageResponseDto;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareLogDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareLogDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDto;
import com.team_3.nursing_care.domain.care_log.service.CareLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.NO_CONTENT;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/care_log")
@RequiredArgsConstructor
public class CareLogController {

    private final CareLogService careLogService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> postCareLog(
            @ModelAttribute ReqCreateCareLogDto reqCareLogDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        careLogService.postCareLog(reqCareLogDto, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(NO_CONTENT, Success, null));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> getCareLogPage(
            @RequestParam(required = false) LocalDate date,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Page<ResCareLogDto> resCareLogDtoPage = careLogService.getCareLogPage(date, pageable, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, new PageResponseDto<>(resCareLogDtoPage)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> getCareLogById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, careLogService.getCareLogById(id, userDetails)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> updateCareLogById(
            @PathVariable Long id,
            @ModelAttribute ReqUpdateCareLogDto reqUpdateCareLogDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        careLogService.updateCareLogById(id, reqUpdateCareLogDto, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(NO_CONTENT, Success, null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> deleteCareLogById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        careLogService.deleteCareLogById(id, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(NO_CONTENT, Success, null));
    }

}
