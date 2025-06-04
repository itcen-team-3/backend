package com.team_3.nursing_care.domain.attendance.constant;

public enum CheckInStatus {

    LATE("지각"),
    ON_TIME("정상 출근");

    private final String checkInStatus;

    CheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public String getCheckInStatus(){
        return checkInStatus;
    }

}
