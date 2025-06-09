package com.team_3.nursing_care.domain.schedule.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CaregiverIdReqDto {

    private Long caregiverId;

    @Builder
    public CaregiverIdReqDto(Long caregiverId) {
        this.caregiverId = caregiverId;
    }
}
