package com.team_3.nursing_care.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttendanceAdminListResDto {

    private List<AttendanceAdminResDto> attendanceList;

    @Builder
    public AttendanceAdminListResDto(List<AttendanceAdminResDto> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public static AttendanceAdminListResDto from(List<AttendanceAdminResDto> attendanceList){
        return AttendanceAdminListResDto.builder()
                .attendanceList(attendanceList)
                .build();
    }

}
