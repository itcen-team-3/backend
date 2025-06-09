package com.team_3.nursing_care.domain.nfc.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;

import java.util.UUID;

public interface NfcService {
    void startWork(UUID uuid, CustomUserDetails userDetails);
    void endWork(UUID uuid, CustomUserDetails userDetails);
}
