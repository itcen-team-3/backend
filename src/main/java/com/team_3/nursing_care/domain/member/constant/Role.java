package com.team_3.nursing_care.domain.member.constant;

public enum Role {

    ADMIN("관리자"),
    CAREGIVER("요양보호사"),
    PATIENT("보호대상자");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
