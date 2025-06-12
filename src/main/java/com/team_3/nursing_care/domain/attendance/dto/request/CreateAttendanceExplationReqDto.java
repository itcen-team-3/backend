package com.team_3.nursing_care.domain.attendance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CreateAttendanceExplationReqDto {

//    private Long attendanceId;
    private Long patientId;
    private String checkInOutStatus;
    private LocalTime attendanceTime;
    private LocalDate attendanceDate;
    private String explation;

    @Builder
    public CreateAttendanceExplationReqDto(String checkInOutStatus,
                                           Long patientId,
                                           LocalTime attendanceTime,
                                           LocalDate attendanceDate,
                                           String explation) {
        this.patientId = patientId;
        this.checkInOutStatus = checkInOutStatus;
        this.attendanceTime = attendanceTime;
        this.attendanceDate = attendanceDate;
        this.explation = explation;
    }
}
