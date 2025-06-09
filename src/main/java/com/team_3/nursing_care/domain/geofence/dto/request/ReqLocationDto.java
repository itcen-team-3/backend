package com.team_3.nursing_care.domain.geofence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqLocationDto {

    private Double latitude;
    private Double longitude;
}
