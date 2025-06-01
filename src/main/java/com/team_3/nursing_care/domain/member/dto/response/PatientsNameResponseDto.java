package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatientsNameResponseDto {

    private Long patientId;
    private String patientName;

    @Builder
    public PatientsNameResponseDto(Long patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public static PatientsNameResponseDto from(Member member){
        return PatientsNameResponseDto.builder()
                .patientId(member.getMemberId())
                .patientName(member.getMemberName())
                .build();
    }
}
