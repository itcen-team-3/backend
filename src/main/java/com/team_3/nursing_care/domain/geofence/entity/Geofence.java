package com.team_3.nursing_care.domain.geofence.entity;


import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.geofence.constant.ShapeType;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqCreateGeofenceDto;
import com.team_3.nursing_care.domain.geofence.service.GeofenceUtil;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geofence")
public class Geofence extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "shape_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShapeType shapeType;

    @Column(name = "shape_geometry", columnDefinition = "GEOMETRY(Geometry, 4326)")
    private Geometry shapeGeometry;

    @Column(name = "radius_meters")
    @Setter
    private Double radiusMeters;

    @Column(name = "is_active")
    @Builder.Default
    @Setter
    private Boolean isActive = true;

    public static Geofence from(ReqCreateGeofenceDto dto) {
        Geofence geofence = Geofence.builder()
                .shapeType(dto.getShapeType())
                .isActive(dto.getIsActive())
                .build();

        if (dto.getShapeType().equals(ShapeType.CIRCLE)) {
            if (dto.getRadiusMeters() == null) throw new IllegalArgumentException("radius_meters must be null");
            geofence.shapeGeometry = GeofenceUtil.createPoint(dto.getCenterLongitude(), dto.getCenterLatitude());
            geofence.radiusMeters = dto.getRadiusMeters();

        } else if (dto.getShapeType().equals(ShapeType.POLYGON)) {
            geofence.shapeGeometry = GeofenceUtil.createPolygon(dto.getVertices());
            geofence.setRadiusMeters(null);
        }

        return geofence;
    }

}
