package com.team_3.nursing_care.domain.geofence.dto.request;

import com.team_3.nursing_care.domain.geofence.constant.ShapeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCreateGeofenceDto {

    private String name;
    private ShapeType shapeType;
    private Double centerLatitude;
    private Double centerLongitude;
    private Double radiusMeters;
    private List<List<Double>> vertices;
    private Boolean isActive = true;

}
