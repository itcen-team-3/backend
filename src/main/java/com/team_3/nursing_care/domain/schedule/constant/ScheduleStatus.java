package com.team_3.nursing_care.domain.schedule.constant;

import java.util.Arrays;

public enum ScheduleStatus {
    PLANNED("예정"),
    ONGOING("진행중"),
    COMPLETED("완료");

    private final String Status;

    ScheduleStatus(String Status) {
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }

}
