package com.team_3.nursing_care.domain.nfc.controller;

import com.team_3.nursing_care.common.exception.ResultMessage;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.nfc.service.NfcService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nfc")
public class NfcController {

    private final NfcService nfcService;

    @PostMapping("/start_work/{uuid}")
    @PreAuthorize("hasAnyRole('CAREGIVER')")
    public ResponseEntity<?> startWork(
            @PathVariable UUID uuid,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        nfcService.startWork(uuid, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatusCode.OK, ResultMessage.Success, null));
    }

    @PostMapping("/end_work/{uuid}")
    public ResponseEntity<?> endWork(
            @PathVariable UUID uuid,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        nfcService.endWork(uuid, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatusCode.OK, ResultMessage.Success, null));
    }

}
