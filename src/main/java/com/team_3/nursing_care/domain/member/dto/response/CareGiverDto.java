package com.team_3.nursing_care.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareGiverDto {

    private Long caregiverId;
    private Boolean workingStatus;
    private String profileImageUrl;

    public static CareGiverDto create(Long caregiverId, CareGiverStatusInfo info) {
        return CareGiverDto.builder()
                .caregiverId(caregiverId)
                .workingStatus(info.getIsWorking())
                .profileImageUrl(info.getProfileImageUrl())
                .build();

    }
}
