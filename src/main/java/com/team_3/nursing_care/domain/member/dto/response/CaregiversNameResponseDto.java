package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CaregiversNameResponseDto {

    private Long caregiverId;
    private String caregiverName;
    private String caregiverProfileUrl;

    @Builder
    public CaregiversNameResponseDto(Long caregiverId, String caregiverName, String caregiverProfileUrl) {
        this.caregiverProfileUrl=caregiverProfileUrl;
        this.caregiverId = caregiverId;
        this.caregiverName = caregiverName;
    }

    public static CaregiversNameResponseDto from(Member member) {
        return CaregiversNameResponseDto.builder()
                .caregiverProfileUrl(member.getProfileImageUrl())
                .caregiverId(member.getMemberId())
                .caregiverName(member.getMemberName())
                .build();
    }
}
