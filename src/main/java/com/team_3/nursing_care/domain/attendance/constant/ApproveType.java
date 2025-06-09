package com.team_3.nursing_care.domain.attendance.constant;

import com.team_3.nursing_care.domain.schedule.constant.PaymentType;

import java.util.Arrays;

public enum ApproveType {
    APPROVED("승인"),
    REJECTED("거절"),
    WAITING("대기");

    private final String approveType;

    ApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApproveTypeName() {
        return approveType;
    }

    public static ApproveType from(String approveType) {
        return Arrays.stream(ApproveType.values())
                .filter(type -> type.getApproveTypeName().equals(approveType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentType: " + approveType));
    }
}
