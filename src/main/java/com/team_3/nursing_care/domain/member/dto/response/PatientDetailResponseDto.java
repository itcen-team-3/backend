package com.team_3.nursing_care.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import com.team_3.nursing_care.domain.schedule.dto.response.CaregiverScheduleResponseDto;
import com.team_3.nursing_care.domain.schedule.dto.response.PatientScheduleResponseDto;
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
public class PatientDetailResponseDto {

    private Long patientId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private int age;
    private String phoneNumber;
    private String address;
    private String description;
    private String profileImage;
    private String guardianPhoneNumber;
    private String relationship;

    private List<PatientScheduleResponseDto> schedules;

    public static PatientDetailResponseDto from(Member member, PatientInfo patientInfo, List<PatientScheduleResponseDto> scheduleDtos , int age) {
        return PatientDetailResponseDto.builder()
                .patientId(member.getMemberId())
                .name(member.getMemberName())
                .birthDate(member.getBirthDate())
                .age(age)
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .profileImage(member.getProfileImageUrl())
                .description(member.getDescription())
                .guardianPhoneNumber(patientInfo.getGuardianPhoneNumber())
                .relationship(patientInfo.getRelationship())
                .schedules(scheduleDtos)
                .build();
    }

}
