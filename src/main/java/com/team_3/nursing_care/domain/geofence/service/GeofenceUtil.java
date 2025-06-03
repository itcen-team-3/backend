package com.team_3.nursing_care.domain.geofence.service;

import com.team_3.nursing_care.common.exception.GeofenceException;
import org.locationtech.jts.geom.*;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

public class GeofenceUtil {

    public static Point createPoint(Double longitude, Double latitude) {
        if (longitude == null || latitude == null)
            throw new IllegalArgumentException("Circle geofence requires center coordinates and radius");
        return new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
    }

    public static Geometry createPolygon(List<List<Double>> coordinates) {
        if (coordinates == null || coordinates.size() < 4)
            throw new IllegalArgumentException("Polygon must have at least 4 coordinates (including closing point). Provided: " + (coordinates == null ? "null" : coordinates.size()));

        Coordinate[] jtsCoordinates = new Coordinate[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            List<Double> vertex = coordinates.get(i);
            if (vertex == null || vertex.size() != 2)
                throw new IllegalArgumentException(String.format("Invalid vertex format at index %d. Each vertex must be a list of [longitude, latitude]. Provided: %s", i, vertex));

            jtsCoordinates[i] = new Coordinate(vertex.get(0), vertex.get(1)); // JTS Coordinate 생성 시 (x, y) 순서 -> (경도, 위도) 순서로 매핑
        }

        if (!jtsCoordinates[0].equals2D(jtsCoordinates[jtsCoordinates.length - 1]))
            throw new IllegalArgumentException("Polygon must be closed (first coordinate must equal the last coordinate).");

        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        LinearRing ring = factory.createLinearRing(jtsCoordinates);

        return factory.createPolygon(ring, null);
    }


    // 경도, 위도
    public static List<List<Double>> extractCoordinatesFromPolygon(Geometry polygon) {
        if (!(polygon instanceof Polygon jtsPolygon))
            throw new GeofenceException(HttpStatusCode.BAD_REQUEST, "Input Geometry is not a Polygon.");

        LinearRing exteriorRing = jtsPolygon.getExteriorRing();
        Coordinate[] coordinates = exteriorRing.getCoordinates();

        List<List<Double>> vertices = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            List<Double> vertex = new ArrayList<>();
            vertex.add(coordinate.getX());
            vertex.add(coordinate.getY());
            vertices.add(vertex);
        }

        return vertices;
    }

}
