package com.team_3.nursing_care.domain.schedule.dto.response;

import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;

@Getter
public class ScheduleDayCaregiverResDto {

    private Long scheduleId;
    private String patientName;
    private Time startTime;
    private Time endTime;
    private String patientAddress;

    @Builder
    public ScheduleDayCaregiverResDto(Long scheduleId,
                                      String patientName,
                                      Time startTime,
                                      Time endTime,
                                      String patientAddress) {
        this.scheduleId = scheduleId;
        this.patientName = patientName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientAddress = patientAddress;
    }

    public static ScheduleDayCaregiverResDto from(Schedule schedule) {
        return ScheduleDayCaregiverResDto.builder()
                .scheduleId(schedule.getScheduleId())
                .patientName(schedule.getPatient())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .patientAddress(schedule.getPatientAddress())
                .build();
    }


}
