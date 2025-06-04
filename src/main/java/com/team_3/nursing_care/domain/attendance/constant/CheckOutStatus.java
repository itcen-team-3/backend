package com.team_3.nursing_care.domain.attendance.constant;

public enum CheckOutStatus {

    ON_TIME("정상 퇴근"),
    EARLY_LEAVE("조퇴");

    private final String checkOutStatus;

    CheckOutStatus(String checkOutStatus) {
        this.checkOutStatus = checkOutStatus;
    }

    public String getCheckOutStatus() {
        return checkOutStatus;
    }

}
