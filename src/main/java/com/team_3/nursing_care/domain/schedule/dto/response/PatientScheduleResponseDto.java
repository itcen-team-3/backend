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
public class PatientScheduleResponseDto {

    private int workDays;
    private String caregiverName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    public static PatientScheduleResponseDto from(Schedule schedule, String caregiverName){
        return PatientScheduleResponseDto.builder()
                .workDays(schedule.getWorkDay())
                .caregiverName(caregiverName)
                .startTime(schedule.getStartTime().toLocalTime())
                .endTime(schedule.getEndTime().toLocalTime())
                .build();
    }

}
