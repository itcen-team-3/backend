package com.team_3.nursing_care.domain.schedule.dto.response;

import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@NoArgsConstructor
public class ScheduleDayAdminResDto {

    private String patientName;
    private String caregiverName;
    private String patientAddress;
    private Time startTime;
    private Time endTime;

    @Builder
    public ScheduleDayAdminResDto(String patientName,
                                  String caregiverName,
                                  String patientAddress,
                                  Time startTime,
                                  Time endTime) {
        this.patientName = patientName;
        this.caregiverName = caregiverName;
        this.patientAddress = patientAddress;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static ScheduleDayAdminResDto from(Schedule schedule, String caregiverName){
        return ScheduleDayAdminResDto.builder()
                .patientName(schedule.getPatient())
                .caregiverName(caregiverName)
                .patientAddress(schedule.getPatientAddress())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}
