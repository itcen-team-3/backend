package com.team_3.nursing_care.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCaregiverResponseDto {
    private Long caregiverId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String certificateNumber;
    private short career;
    private String description;
    private String profileImage;

    @Builder
    public UpdateCaregiverResponseDto(Long caregiverId,
                                      String name,
                                      LocalDate birthDate,
                                      String phoneNumber,
                                      String address,
                                      String certificateNumber,
                                      short career,
                                      String description,
                                      String profileImage) {
        this.caregiverId = caregiverId;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.certificateNumber = certificateNumber;
        this.career = career;
        this.description = description;
        this.profileImage = profileImage;
    }

    public static UpdateCaregiverResponseDto from(Member member) {
        return UpdateCaregiverResponseDto.builder()
                .caregiverId(member.getMemberId())
                .name(member.getMemberName())
                .birthDate(member.getBirthDate())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .certificateNumber(member.getCertificateNumber())
                .career(member.getCareer())
                .description(member.getDescription())
                .profileImage(member.getProfileImageUrl())
                .build();
    }
}