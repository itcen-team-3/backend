package com.team_3.nursing_care.domain.geofence.controller;

import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqCreateGeofenceDto;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqLocationDto;
import com.team_3.nursing_care.domain.geofence.service.GeofenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geofence")
public class GeofenceController {

    private final GeofenceService geofenceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> createGeofence(@RequestBody ReqCreateGeofenceDto reqCreateGeofenceDto) {
        return ResponseEntity.ok(new ResponseDto(CREATED, Success, geofenceService.createGeofence(reqCreateGeofenceDto)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> getGeofenceByLocation(@ModelAttribute ReqLocationDto reqLocationDto) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, geofenceService.getGeofenceByLocation(reqLocationDto)));
    }

    @GetMapping("/all/true")
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> getAllIsActiveTrue() {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, geofenceService.getAllIsActiveTrue()));
    }

    @GetMapping("/all/false")
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> getAllIsActiveFalse() {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, geofenceService.getAllIsActiveFalse()));
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> patchGeofenceIsActive(@PathVariable UUID id) {
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, geofenceService.patchIsActive(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CAREGIVER', 'ADMIN')")
    public ResponseEntity<?> deleteGeofence(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id) {
        geofenceService.deleteGeofence(id, userDetails);
        return ResponseEntity.ok(new ResponseDto<>(NO_CONTENT, Success, null));
    }

}

