package com.team_3.nursing_care.domain.attendance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateApprovementTypeReqDto {

    private String approveType;
    private String rejectReason;

    @Builder
    public UpdateApprovementTypeReqDto(String approveType, String rejectReason) {
        this.approveType = approveType;
        this.rejectReason = rejectReason;
    }
}
