package com.team_3.nursing_care.domain.geofence.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqCreateGeofenceDto;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqLocationDto;
import com.team_3.nursing_care.domain.geofence.dto.response.ResGeofenceDto;

import java.util.List;
import java.util.UUID;

public interface GeofenceService {

    ResGeofenceDto createGeofence(ReqCreateGeofenceDto reqCreateGeofenceDto);

    List<ResGeofenceDto> getGeofenceByLocation(ReqLocationDto reqLocationDto);

    List<ResGeofenceDto> getAllIsActiveTrue();

    List<ResGeofenceDto> getAllIsActiveFalse();

    ResGeofenceDto patchIsActive(UUID id);

    void deleteGeofence(UUID id, CustomUserDetails userDetails);

}
