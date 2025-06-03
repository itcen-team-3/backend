package com.team_3.nursing_care.domain.geofence.dto.response;

import com.team_3.nursing_care.domain.geofence.constant.ShapeType;
import com.team_3.nursing_care.domain.geofence.entity.Geofence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResGeofenceDto {

    private UUID geofenceId;
    private String name;
    private ShapeType shapeType;

    private Double centerLatitude;
    private Double centerLongitude;
    private Double radiusMeters;
    private List<List<Double>> vertices;
    private Boolean isActive;

    public static ResGeofenceDto create(Geofence geofence) {
        ResGeofenceDto dto = ResGeofenceDto.builder()
                .geofenceId(geofence.getId())
                .name(geofence.getName())
                .shapeType(geofence.getShapeType())
                .isActive(geofence.getIsActive())
                .build();

        if (geofence.getShapeType() == ShapeType.CIRCLE) {
            Coordinate coordinate = geofence.getShapeGeometry().getCoordinate();
            dto.setCenterLongitude(coordinate.getX());
            dto.setCenterLatitude(coordinate.getY());
            dto.setRadiusMeters(geofence.getRadiusMeters());

        } else if (geofence.getShapeType() == ShapeType.POLYGON) {
            Coordinate[] coordinates = geofence.getShapeGeometry().getCoordinates();
            List<List<Double>> vertices = new ArrayList<>();
            for (Coordinate coordinate : coordinates) {
                double longitude = coordinate.getX();
                double latitude = coordinate.getY();
                vertices.add(List.of(longitude, latitude));
            }

            dto.setVertices(vertices);
        }

        return dto;
    }
}
