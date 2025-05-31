package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CaregiversNameListResponseDto {

    private List<CaregiversNameResponseDto> caregivers;

    @Builder
    public CaregiversNameListResponseDto(List<CaregiversNameResponseDto> caregivers) {
        this.caregivers = caregivers;
    }

    public static CaregiversNameListResponseDto from(List<CaregiversNameResponseDto> caregivers) {
        return CaregiversNameListResponseDto.builder()
                .caregivers(caregivers)
                .build();
    }
}
