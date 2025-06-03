package com.team_3.nursing_care.domain.member.constant;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("관리자", Authority.ADMIN),
    CAREGIVER("요양보호사", Authority.CAREGIVER),
    PATIENT("보호대상자", Authority.PATIENT),;

    private final String displayName;
    private final String authority;

    Role(String displayName, String authority) {
        this.displayName = displayName;
        this.authority = authority;
    }

    public static class Authority {
        public static final String PATIENT = "ROLE_PATIENT";
        public static final String CAREGIVER = "ROLE_CAREGIVER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}
