package com.team_3.nursing_care.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.schedule.dto.response.CaregiverScheduleResponseDto;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class CaregiverDetailResponseDto {

    private Long caregiverId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private int age;
    private String phoneNumber;
    private String address;
    private String description;
    private String profileImage;


    private List<CaregiverScheduleResponseDto> schedules;

    public static CaregiverDetailResponseDto from(Member member, List<Schedule> schedules, int age) {
        return CaregiverDetailResponseDto.builder()
                .caregiverId(member.getMemberId())
                .name(member.getMemberName())
                .birthDate(member.getBirthDate())
                .age(age)
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .profileImage(member.getProfileImageUrl())
                .description(member.getDescription())
                .schedules(schedules.stream()
                        .map(CaregiverScheduleResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }

}