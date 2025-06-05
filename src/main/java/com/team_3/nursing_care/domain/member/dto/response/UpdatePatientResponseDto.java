package com.team_3.nursing_care.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdatePatientResponseDto {

    private Long patientId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String patientLevel;
    private String guardianPhoneNumber;
    private String relationship;
    private String description;
    private String profileImage;

    @Builder
    public UpdatePatientResponseDto(Long patientId,
                                    String name,
                                    LocalDate birthDate,
                                    String phoneNumber,
                                    String address,
                                    String patientLevel,
                                    String guardianPhoneNumber,
                                    String relationship,
                                    String description,
                                    String profileImage) {
        this.patientId = patientId;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.patientLevel = patientLevel;
        this.guardianPhoneNumber = guardianPhoneNumber;
        this.relationship = relationship;
        this.description = description;
        this.profileImage = profileImage;
    }

    public static UpdatePatientResponseDto from(Member member, PatientInfo patientInfo) {
        return UpdatePatientResponseDto.builder()
                .patientId(member.getMemberId())
                .name(member.getMemberName())
                .birthDate(member.getBirthDate())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .patientLevel(patientInfo.getPatientLevel())
                .guardianPhoneNumber(patientInfo.getGuardianPhoneNumber())
                .relationship(patientInfo.getRelationship())
                .description(member.getDescription())
                .profileImage(member.getProfileImageUrl())
                .build();
    }

}
