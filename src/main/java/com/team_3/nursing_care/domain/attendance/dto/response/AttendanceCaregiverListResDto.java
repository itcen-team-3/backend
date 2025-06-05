package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttendanceCaregiverListResDto {

    private List<AttendanceCaregiverResDto> attendances;

    @Builder
    public AttendanceCaregiverListResDto(List<AttendanceCaregiverResDto> attendances) {
        this.attendances = attendances;
    }

    public static AttendanceCaregiverListResDto from(List<AttendanceCaregiverResDto> attendances) {
        return AttendanceCaregiverListResDto.builder()
                .attendances(attendances)
                .build();
    }
}
