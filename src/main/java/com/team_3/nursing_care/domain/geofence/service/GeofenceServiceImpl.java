package com.team_3.nursing_care.domain.geofence.service;

import com.team_3.nursing_care.common.exception.GeofenceException;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqCreateGeofenceDto;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqLocationDto;
import com.team_3.nursing_care.domain.geofence.dto.response.ResGeofenceDto;
import com.team_3.nursing_care.domain.geofence.entity.Geofence;
import com.team_3.nursing_care.domain.geofence.repository.GeofenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeofenceServiceImpl implements GeofenceService {

    private final GeofenceRepository geofenceRepository;

    @Override
    @Transactional
    public ResGeofenceDto createGeofence(ReqCreateGeofenceDto reqCreateGeofenceDto) {
        Geofence geofence = Geofence.from(reqCreateGeofenceDto);
        return ResGeofenceDto.create(geofenceRepository.save(geofence));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResGeofenceDto> getGeofenceByLocation(ReqLocationDto reqLocationDto) {
        Point point = GeofenceUtil.createPoint(reqLocationDto.getLongitude(), reqLocationDto.getLatitude());
        return geofenceRepository.findActiveGeofenceHitByLocation(point)
                .stream()
                .map(ResGeofenceDto::create)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResGeofenceDto> getAllIsActiveTrue() {
        return geofenceRepository.findByIsActiveTrueAndIsDeletedFalse().stream()
                .map(ResGeofenceDto::create)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResGeofenceDto> getAllIsActiveFalse() {
        return geofenceRepository.findByIsActiveFalseAndIsDeletedFalse().stream()
                .map(ResGeofenceDto::create)
                .toList();
    }

    @Override
    @Transactional
    public ResGeofenceDto patchIsActive(UUID id) {
        Geofence geofence = geofenceRepository.findById(id).orElseThrow(() -> new GeofenceException(HttpStatusCode.BAD_REQUEST, "not found geofence by id: " + id));
        geofence.setIsActive(!geofence.getIsActive());
        return ResGeofenceDto.create(geofence);
    }

    @Override
    public void deleteGeofence(UUID id, CustomUserDetails userDetails) {
        Geofence geofence = geofenceRepository.findById(id).orElseThrow(() -> new GeofenceException(HttpStatusCode.NOT_FOUND, "not found geofence by id: " + id));
        geofence.softDelete(userDetails.getMemberId());
    }
}
