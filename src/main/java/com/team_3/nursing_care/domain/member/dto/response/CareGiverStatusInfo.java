package com.team_3.nursing_care.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareGiverStatusInfo {

    private Boolean isWorking;
    private String profileImageUrl;

}
