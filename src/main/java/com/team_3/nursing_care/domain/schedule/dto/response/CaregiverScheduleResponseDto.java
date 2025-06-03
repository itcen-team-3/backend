package com.team_3.nursing_care.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaregiverScheduleResponseDto {

    private int workDays;
    private String patientName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    public static CaregiverScheduleResponseDto from(Schedule schedule){
        return CaregiverScheduleResponseDto.builder()
                .workDays(schedule.getWorkDay())
                .patientName(schedule.getPatient())
                .startTime(schedule.getStartTime().toLocalTime())
                .endTime(schedule.getEndTime().toLocalTime())
                .build();
    }
}