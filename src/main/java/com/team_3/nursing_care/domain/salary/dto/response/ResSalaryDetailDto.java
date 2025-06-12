package com.team_3.nursing_care.domain.salary.dto.response;

import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResSalaryDetailDto {

    private LocalDate workDate;
    private DayOfWeek dayOfWeek;
    private Time startWorkTime;
    private Time endWorkTime;

    private Time totalWorkTime;
    private CheckInStatus checkInStatus;
    private CheckOutStatus checkOutStatus;

    public static ResSalaryDetailDto create(AttendanceLog ll) {

        LocalDateTime startLocalDateTime = ll.getCheckIn() != null ? ll.getCheckIn() : null;
        LocalDateTime endLocalDateTime = ll.getCheckOut() != null ? ll.getCheckOut() : null;
        LocalDate workDate = ll.getCreateDate().toLocalDate();

        Time startWorkTime = null;
        Time endWorkTime = null;
        if (startLocalDateTime != null) startWorkTime = Time.valueOf(startLocalDateTime.toLocalTime());
        else if (endLocalDateTime != null) endWorkTime = Time.valueOf(endLocalDateTime.toLocalTime());

        Time totalWorkTime = null;

        if (startLocalDateTime != null && endLocalDateTime != null) {
            Duration duration = Duration.between(startLocalDateTime, endLocalDateTime).abs();
            LocalTime timeRepresentationOfDuration = LocalTime.MIDNIGHT.plus(duration);
            totalWorkTime = Time.valueOf(timeRepresentationOfDuration);
        }

        return ResSalaryDetailDto.builder()
                .workDate(workDate)
                .dayOfWeek(workDate.getDayOfWeek())
                .startWorkTime(startWorkTime)
                .endWorkTime(endWorkTime)
                .totalWorkTime(totalWorkTime)
                .checkInStatus(ll.getCheckInStatus())
                .checkOutStatus(ll.getCheckOutStatus())
                .build();
    }
}
