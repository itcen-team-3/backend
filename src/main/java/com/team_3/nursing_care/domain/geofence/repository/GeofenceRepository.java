package com.team_3.nursing_care.domain.geofence.repository;

import com.team_3.nursing_care.domain.geofence.entity.Geofence;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GeofenceRepository extends JpaRepository<Geofence, UUID> {

    List<Geofence> findByIsActiveTrueAndIsDeletedFalse();

    List<Geofence> findByIsActiveFalseAndIsDeletedFalse();

    // POLYGON
    @Query(
            value = "select * from geofence g " +
                    "where g.is_active = true " +
                    "and ST_Contains(g.shape_geometry, ST_SetSRID(:currentLocation, 4326))",
            nativeQuery = true
    )
    List<Geofence> findActiveGeofenceContainingLocation(@Param("currentLocation") Point currentLocation);

    // CIRCLE
    @Query(
            value = "select * from geofence g " +
                    "where g.is_active = true " +
                    "and g.shape_type = 'CIRCLE' " +
                    "and ST_DWithin(g.shape_geometry, ST_SetSRID(:currentLocation, 4326), g.radius_meters, true)",
            nativeQuery = true
    )
    List<Geofence> findActiveCircularGeofenceWithinDistance(@Param("currentLocation") Point currentLocation);

    // BOTH
    @Query(
            value = "select * from geofence g " +
                    "where g.is_active = true " +
                    "and (" +
                    "(g.shape_type = 'POLYGON' AND ST_Contains(g.shape_geometry, ST_SetSRID(:currentLocation, 4326))) " +
                    "OR (g.shape_type = 'CIRCLE' AND ST_DWithin(g.shape_geometry, ST_SetSRID(:currentLocation, 4326), g.radius_meters, true)))"
            , nativeQuery = true
    )
    List<Geofence> findActiveGeofenceHitByLocation(@Param("currentLocation") Point currentLocation);


}
