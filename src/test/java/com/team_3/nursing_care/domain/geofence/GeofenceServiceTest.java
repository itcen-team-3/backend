package com.team_3.nursing_care.domain.geofence;

import com.team_3.nursing_care.common.exception.GeofenceException;
import com.team_3.nursing_care.domain.geofence.constant.ShapeType;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqCreateGeofenceDto;
import com.team_3.nursing_care.domain.geofence.dto.request.ReqLocationDto;
import com.team_3.nursing_care.domain.geofence.dto.response.ResGeofenceDto;
import com.team_3.nursing_care.domain.geofence.entity.Geofence;
import com.team_3.nursing_care.domain.geofence.repository.GeofenceRepository;
import com.team_3.nursing_care.domain.geofence.service.GeofenceServiceImpl;
import com.team_3.nursing_care.domain.geofence.service.GeofenceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeofenceServiceTest {

    @InjectMocks
    private GeofenceServiceImpl geofenceService;

    @Mock
    private GeofenceRepository geofenceRepository;

    @Test
    @DisplayName("Polygon 지오펜스 생성 성공")
    public void createGeofence_Polygon_Success() {
        // GIVEN
        List<List<Double>> vertices = List.of(
                List.of(126.991189, 37.417159),
                List.of(126.989869, 37.416584),
                List.of(126.989670, 37.415766),
                List.of(126.991859, 37.415608),
                List.of(126.991189, 37.417159)
        );
        ReqCreateGeofenceDto requestDto = ReqCreateGeofenceDto.builder()
                .name("TEST_NAME")
                .shapeType(ShapeType.POLYGON)
                .vertices(vertices)
                .centerLatitude(null)
                .centerLongitude(null)
                .radiusMeters(null)
                .isActive(true)
                .build();

        Geofence fromEntity = Geofence.from(requestDto);
        Geofence saveEntity = Geofence.builder()
                .id(UUID.randomUUID())
                .name("TEST_NAME")
                .shapeType(ShapeType.POLYGON)
                .shapeGeometry(GeofenceUtil.createPolygon(vertices))
                .radiusMeters(null)
                .isActive(true)
                .build();

        ResGeofenceDto responseDto = ResGeofenceDto.create(saveEntity);

        try (MockedStatic<Geofence> mockedGeofenceStatic = Mockito.mockStatic(Geofence.class);
             MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {

            mockedGeofenceStatic.when(() -> Geofence.from(requestDto))
                    .thenReturn(fromEntity);

            when(geofenceRepository.save(fromEntity))
                    .thenReturn(saveEntity);

            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(saveEntity))
                    .thenReturn(responseDto);

            // WHEN
            ResGeofenceDto actualResponseDto = geofenceService.createGeofence(requestDto);

            // THEN
            assertNotNull(actualResponseDto, "NOT NULL DTO");
            assertEquals(responseDto, actualResponseDto, "NOT MATCH EXPECTED");

            mockedGeofenceStatic.verify(() -> Geofence.from(requestDto), times(1));
            verify(geofenceRepository, times(1)).save(fromEntity);

            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(saveEntity), times(1));
        }
    }

    @Test
    @DisplayName("CIRCLE 지오펜스 생성 성공")
    public void CREATE_CIRCLE_GEOFENCE() {
        // GIVEN
        Double latitude = 37.417159;
        Double longitude = 126.991189;
        Double radiusMeters = 30.111;
        ReqCreateGeofenceDto requestDto = ReqCreateGeofenceDto.builder()
                .name("TEST_NAME")
                .shapeType(ShapeType.CIRCLE)
                .vertices(null)
                .centerLatitude(latitude)
                .centerLongitude(longitude)
                .radiusMeters(radiusMeters)
                .isActive(true)
                .build();

        Geofence fromEntity = Geofence.from(requestDto);
        Geofence saveEntity = Geofence.builder()
                .id(UUID.randomUUID())
                .name("TEST_NAME")
                .shapeType(ShapeType.CIRCLE)
                .shapeGeometry(GeofenceUtil.createPoint(longitude, latitude))
                .radiusMeters(radiusMeters)
                .isActive(true)
                .build();

        ResGeofenceDto responseDto = ResGeofenceDto.create(saveEntity);

        try (MockedStatic<Geofence> mockedGeofenceStatic = Mockito.mockStatic(Geofence.class);
             MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {

            mockedGeofenceStatic.when(() -> Geofence.from(requestDto))
                    .thenReturn(fromEntity);

            when(geofenceRepository.save(fromEntity))
                    .thenReturn(saveEntity);

            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(saveEntity))
                    .thenReturn(responseDto);

            // WHEN
            ResGeofenceDto actualResponseDto = geofenceService.createGeofence(requestDto);

            // THEN
            assertNotNull(actualResponseDto, "NOT NULL DTO");
            assertEquals(responseDto, actualResponseDto, "NOT MATCH EXPECTED");

            mockedGeofenceStatic.verify(() -> Geofence.from(requestDto), times(1));
            verify(geofenceRepository, times(1)).save(fromEntity);

            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(saveEntity), times(1));
        }
    }

    @Test
    @DisplayName("위치 내 지오펜스 리스트 조회 성공")
    public void GET_GEOFENCE_BY_LOCATION() {
        // GIVEN
        Double latitude = 37.417159;
        Double longitude = 126.991189;

        ReqLocationDto requestDto = ReqLocationDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        Point mockPoint = mock(Point.class);

        Geofence mockGeofence_A = Geofence.builder()
                .id(UUID.randomUUID())
                .name("Geofence A")
                .shapeType(ShapeType.POLYGON)
                .shapeGeometry(GeofenceUtil.createPolygon(List.of(
                        List.of(126.991189, 37.417159),
                        List.of(126.989869, 37.416584),
                        List.of(126.989670, 37.415766),
                        List.of(126.991859, 37.415608),
                        List.of(126.991189, 37.417159)
                )))
                .isActive(true)
                .build();
        Geofence mockGeofence_B = Geofence.builder()
                .id(UUID.randomUUID())
                .name("Geofence B")
                .shapeType(ShapeType.CIRCLE)
                .shapeGeometry(GeofenceUtil.createPoint(longitude, latitude))
                .radiusMeters(50.0)
                .isActive(true)
                .build();
        List<Geofence> mockGeofenceList = Arrays.asList(mockGeofence_A, mockGeofence_B);

        ResGeofenceDto mockResDto_A = ResGeofenceDto.create(mockGeofence_A);
        ResGeofenceDto mockResDto_B = ResGeofenceDto.create(mockGeofence_B);
        List<ResGeofenceDto> expectedResponseList = Arrays.asList(mockResDto_A, mockResDto_B);


        try (MockedStatic<GeofenceUtil> mockedGeofenceUtilStatic = Mockito.mockStatic(GeofenceUtil.class);
             MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {

            mockedGeofenceUtilStatic.when(() -> GeofenceUtil.createPoint(eq(requestDto.getLongitude()), eq(requestDto.getLatitude())))
                    .thenReturn(mockPoint);

            when(geofenceRepository.findActiveGeofenceHitByLocation(mockPoint))
                    .thenReturn(mockGeofenceList);

            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(mockGeofence_A))
                    .thenReturn(mockResDto_A);
            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(mockGeofence_B))
                    .thenReturn(mockResDto_B);

            // WHEN
            List<ResGeofenceDto> actualResponseList = geofenceService.getGeofenceByLocation(requestDto);

            // THEN
            assertThat(actualResponseList)
                    .isNotNull()
                    .hasSize(expectedResponseList.size());

            mockedGeofenceUtilStatic.verify(() -> GeofenceUtil.createPoint(eq(requestDto.getLongitude()), eq(requestDto.getLatitude())), times(1));

            verify(geofenceRepository, times(1)).findActiveGeofenceHitByLocation(mockPoint);

            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(any(Geofence.class)), times(2));
        }
    }

    @Test
    @DisplayName("활성화된 지오펜스 리스트 조회 성공")
    public void GET_ALL_IS_ACTIVE_TRUE_WHEN_EXISTED() {
        Geofence mockGeofence = Geofence.builder()
                .id(UUID.randomUUID())
                .name("TEST_GEOFENCE")
                .shapeType(ShapeType.CIRCLE)
                .shapeGeometry(GeofenceUtil.createPoint(127.512342, 37.417159))
                .radiusMeters(50.0)
                .isActive(true)
                .build();
        List<Geofence> mockGeofenceList = Collections.singletonList(mockGeofence);

        ResGeofenceDto responseDto = ResGeofenceDto.create(mockGeofence);
        List<ResGeofenceDto> expectedResponseList = Collections.singletonList(responseDto);

        try (MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {

            when(geofenceRepository.findByIsActiveTrueAndIsDeletedFalse()).thenReturn(mockGeofenceList);
            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(mockGeofence)).thenReturn(responseDto);

            // WHEN
            List<ResGeofenceDto> actualResponseList = geofenceService.getAllIsActiveTrue();

            // THEN
            assertThat(actualResponseList)
                    .isNotNull()
                    .hasSize(expectedResponseList.size())
                    .isEqualTo(expectedResponseList);

            verify(geofenceRepository, times(1)).findByIsActiveTrueAndIsDeletedFalse();
            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(mockGeofence), times(1));
        }
    }

    @Test
    @DisplayName("비활성화된 지오펜스 리스트 조회 성공")
    public void GET_ALL_IS_ACTIVE_FALSE() {
        Geofence mockGeofence = Geofence.builder()
                .id(UUID.randomUUID())
                .name("TEST_GEOFENCE")
                .shapeType(ShapeType.CIRCLE)
                .shapeGeometry(GeofenceUtil.createPoint(127.512342, 37.417159))
                .radiusMeters(50.0)
                .isActive(false)
                .build();
        List<Geofence> mockGeofenceList = Collections.singletonList(mockGeofence);

        ResGeofenceDto responseDto = ResGeofenceDto.create(mockGeofence);
        List<ResGeofenceDto> expectedResponseList = Collections.singletonList(responseDto);

        try (MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {
            when(geofenceRepository.findByIsActiveFalseAndIsDeletedFalse()).thenReturn(mockGeofenceList);
            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(mockGeofence)).thenReturn(responseDto);

            // WHEN
            List<ResGeofenceDto> actualResponseList = geofenceService.getAllIsActiveFalse();

            // THEN
            assertThat(actualResponseList)
                    .isNotNull()
                    .hasSize(expectedResponseList.size())
                    .isEqualTo(expectedResponseList);

            verify(geofenceRepository, times(1)).findByIsActiveFalseAndIsDeletedFalse();
            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(mockGeofence), times(1));
        }
    }

    @Test
    @DisplayName("지오펜스 수정 ( 비활성화 -> 활성화 )")
    public void PATCH_GEOFENCE_TRUE() {
        UUID uuid = UUID.randomUUID();
        Geofence mockGeofence = Geofence.builder()
                .id(uuid)
                .name("TEST_GEOFENCE")
                .shapeType(ShapeType.CIRCLE)
                .shapeGeometry(GeofenceUtil.createPoint(127.512342, 37.417159))
                .radiusMeters(50.0)
                .isActive(false)
                .build();
        ResGeofenceDto expectedResponseDto = ResGeofenceDto.create(mockGeofence);
        expectedResponseDto.setIsActive(!expectedResponseDto.getIsActive());

        when(geofenceRepository.findById(uuid)).thenReturn(Optional.of(mockGeofence));

        try (MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {

            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(mockGeofence)).thenReturn(expectedResponseDto);

            // WHEN
            ResGeofenceDto actualResponseDto = geofenceService.patchIsActive(uuid);

            // THEN
            verify(geofenceRepository, times(1)).findById(uuid);

            assertThat(mockGeofence.getIsActive()).isTrue();

            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(mockGeofence), times(1));
            assertThat(actualResponseDto).isEqualTo(expectedResponseDto);
        }
    }

    @Test
    @DisplayName("지오펜스 수정 ( 활성화 -> 비활성화 )")
    public void PATCH_GEOFENCE_FALSE() {
        UUID uuid = UUID.randomUUID();
        Geofence mockGeofence = Geofence.builder()
                .id(uuid)
                .name("TEST_GEOFENCE")
                .shapeType(ShapeType.CIRCLE)
                .shapeGeometry(GeofenceUtil.createPoint(127.512342, 37.417159))
                .radiusMeters(50.0)
                .isActive(true)
                .build();

        ResGeofenceDto expectedResponseDto = ResGeofenceDto.create(mockGeofence);
        System.out.println("expectedResponseDto = " + expectedResponseDto);
        expectedResponseDto.setIsActive(!expectedResponseDto.getIsActive());

        when(geofenceRepository.findById(uuid)).thenReturn(Optional.of(mockGeofence));

        try (MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {

            mockedResGeofenceDtoStatic.when(() -> ResGeofenceDto.create(mockGeofence)).thenReturn(expectedResponseDto);

            // WHEN
            ResGeofenceDto actualResponseDto = geofenceService.patchIsActive(uuid);

            // THEN
            verify(geofenceRepository, times(1)).findById(uuid);

            assertThat(mockGeofence.getIsActive()).isFalse();

            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(mockGeofence), times(1));
            assertThat(actualResponseDto).isEqualTo(expectedResponseDto);
        }
    }

    @Test
    @DisplayName("지오펜스 수정 ( 예외 발생 )")
    public void PATCH_GEOFENCE_EXCEPTION() {
        UUID nonExistedUUID = UUID.randomUUID();

        when(geofenceRepository.findById(nonExistedUUID)).thenReturn(Optional.empty());

        GeofenceException exception = Assertions.assertThrows(GeofenceException.class, () -> geofenceService.patchIsActive(nonExistedUUID));

        assertThat(exception.getMessage()).isEqualTo("not found geofence by id: " + nonExistedUUID);

        verify(geofenceRepository, times(1)).findById(nonExistedUUID);

        try (MockedStatic<ResGeofenceDto> mockedResGeofenceDtoStatic = Mockito.mockStatic(ResGeofenceDto.class)) {
            mockedResGeofenceDtoStatic.verify(() -> ResGeofenceDto.create(any(Geofence.class)), never());
        }
    }

    @Test
    @DisplayName("지오펜스 삭제 성공")
    public void DELETE_GEOFENCE() {

        // TODO: MEMBER 완성된 이후 진행

    }

    @Test
    @DisplayName("지오펜스 삭제 ( 예외 발생 )")
    public void DELETE_GEOFENCE_EXCEPTION() {

        // TODO: MEMBER 완성된 이후 진행

    }


}
