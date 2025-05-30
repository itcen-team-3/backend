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
    private String name;

    @Builder
    public CaregiversNameResponseDto(Long caregiverId, String name) {
        this.caregiverId = caregiverId;
        this.name = name;
    }

    public static CaregiversNameResponseDto from(Member member) {
        return CaregiversNameResponseDto.builder()
                .caregiverId(member.getMemberId())
                .name(member.getMemberName())
                .build();
    }
}
