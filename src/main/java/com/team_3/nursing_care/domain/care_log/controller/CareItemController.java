package com.team_3.nursing_care.domain.care_log.controller;

import com.team_3.nursing_care.common.exception.ResultMessage;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareItemDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareItemDto;
import com.team_3.nursing_care.domain.care_log.service.CareItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static software.amazon.awssdk.http.HttpStatusCode.NO_CONTENT;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

/**
 * 관리자 페이지에서 CareItem 항목을 관리하는 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/care_item")
public class CareItemController {

    private final CareItemService careItemService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createCareItem(@RequestBody ReqCreateCareItemDto reqCreateCareItemDto) {
        careItemService.createCareItem(reqCreateCareItemDto);
        return ResponseEntity.ok(new ResponseDto<>(NO_CONTENT, ResultMessage.Success, null));
    }

    @GetMapping
    public ResponseEntity<?> getCareItemListIsDeletedFalse() {
        return ResponseEntity.ok(new ResponseDto<>(OK, ResultMessage.Success, careItemService.getCareItemListIsDeletedFalse()));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getCareItemList() {
        return ResponseEntity.ok(new ResponseDto<>(OK, ResultMessage.Success, careItemService.getCareItemList()));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateCareItemById(
            @PathVariable Integer id,
            @RequestBody ReqUpdateCareItemDto reqUpdateCareItemDto
    ) {
        return ResponseEntity.ok(new ResponseDto<>(OK, ResultMessage.Success, careItemService.updateCareItemById(id, reqUpdateCareItemDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteCareItemById(
            @PathVariable Integer id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        careItemService.deleteCareItemById(id, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(NO_CONTENT, ResultMessage.Success, null));
    }
}
