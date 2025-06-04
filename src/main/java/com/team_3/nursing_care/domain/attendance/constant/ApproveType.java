package com.team_3.nursing_care.domain.attendance.constant;

public enum ApproveType {
    APPROVED("승인"),
    REJECTED("거절"),
    WAITING("대기");

    private final String approveType;

    ApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApproveType() {
        return approveType;
    }
}
