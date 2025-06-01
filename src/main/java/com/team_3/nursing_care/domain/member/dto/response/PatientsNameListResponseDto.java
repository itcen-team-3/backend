package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PatientsNameListResponseDto {

    private List<PatientsNameResponseDto> patients;

    @Builder
    public PatientsNameListResponseDto(List<PatientsNameResponseDto> patients) {
        this.patients = patients;
    }

    public static PatientsNameListResponseDto from(List<PatientsNameResponseDto> patients){
        return PatientsNameListResponseDto.builder()
                .patients(patients)
                .build();
    }
}
