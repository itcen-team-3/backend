package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@AllArgsConstructor
public class PatientListResponseDto {

    private Long patientId;
    private String name;
    private int age;
    private String profileImage;

    public static PatientListResponseDto from(Member member){
        return new PatientListResponseDto(
                member.getMemberId(),
                member.getMemberName(),
                calculateAge(member.getBirthDate()),
                member.getProfileImageUrl()
        );
    }

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birthDate must not be null");
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
