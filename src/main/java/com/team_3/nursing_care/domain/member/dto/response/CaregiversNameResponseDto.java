package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CaregiversNameResponseDto {

    private Long caregiverId;
    private String caregiverName;

    @Builder
    public CaregiversNameResponseDto(Long caregiverId, String caregiverName) {
        this.caregiverId = caregiverId;
        this.caregiverName = caregiverName;
    }

    public static CaregiversNameResponseDto from(Member member) {
        return CaregiversNameResponseDto.builder()
                .caregiverId(member.getMemberId())
                .caregiverName(member.getMemberName())
                .build();
    }
}
