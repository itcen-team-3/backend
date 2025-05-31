package com.team_3.nursing_care.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CaregiversNameListResponseDto {

    private List<CaregiversNameResponseDto> caregivers;

}
