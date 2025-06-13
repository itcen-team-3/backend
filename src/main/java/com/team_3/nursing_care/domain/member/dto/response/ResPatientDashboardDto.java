package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResPatientDashboardDto {

    private String guardianName;
    private List<CareGiverDto> careGiverDtoList;
    private List<CareLogDto> careLogList;

    public static ResPatientDashboardDto create(Map<Long, CareGiverStatusInfo> careGiverStatusMap, List<CareLog> careLogList, PatientInfo patientInfo) {
        List<CareGiverDto> careGiverDtoList = new ArrayList<>();
        List<CareLogDto> careLogDtoList = new ArrayList<>();

        for (Map.Entry<Long, CareGiverStatusInfo> entry : careGiverStatusMap.entrySet())
            careGiverDtoList.add(CareGiverDto.create(entry.getKey(), entry.getValue()));

        for (CareLog careLog : careLogList)
            careLogDtoList.add(CareLogDto.create(careLog));

        return ResPatientDashboardDto.builder()
                .guardianName(patientInfo.getGuardianName())
                .careGiverDtoList(careGiverDtoList)
                .careLogList(careLogDtoList)
                .build();
    }
}
