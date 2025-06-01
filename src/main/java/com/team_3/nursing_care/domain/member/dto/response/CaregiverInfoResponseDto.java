package com.team_3.nursing_care.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CaregiverInfoResponseDto {
    private Long caregiverId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String description;
    private String profileImage;

    @Builder
    public CaregiverInfoResponseDto(Long caregiverId,
                                    String name,
                                    LocalDate birthDate,
                                    String phoneNumber,
                                    String address,
                                    String description,
                                    String profileImage){
        this.caregiverId = caregiverId;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.profileImage = profileImage;
    }
}
