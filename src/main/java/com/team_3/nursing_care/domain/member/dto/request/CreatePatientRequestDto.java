package com.team_3.nursing_care.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CreatePatientRequestDto {

    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    @NotNull(message = "생년월일은 필수값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    @NotBlank(message = "주소는 필수값입니다.")
    private String address;

    @NotBlank(message = "장기요양등급은 필수값입니다.")
    private String patientLevel;

    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String guardianPhoneNumber;

    @NotBlank(message = "가족 관계는 필수값입니다.")
    private String relationship;

    @NotBlank(message = "보호대상자 이름은 필수값입니다.")
    private String guardianName;

    private String description;

    @Builder
    public CreatePatientRequestDto(
            String name,
            LocalDate birthDate,
            String phoneNumber,
            String address,
            String patientLevel,
            String guardianPhoneNumber,
            String relationship,
            String guardianName,
            String description) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.patientLevel = patientLevel;
        this.guardianPhoneNumber = guardianPhoneNumber;
        this.relationship = relationship;
        this.guardianName = guardianName;
        this.description = description;
    }

    public Member toMember(String profileImageUrl, Company company, Role role) {
        return Member.builder()
                .company(company)
                .memberName(this.name)
                .birthDate(this.birthDate)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .description(this.description)
                .role(role)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public PatientInfo toPatient() {
        return PatientInfo.builder()
                .patientLevel(this.patientLevel)
                .guardianPhoneNumber(this.guardianPhoneNumber)
                .relationship(this.relationship)
                .guardianName(this.guardianName)
                .nfcUuid(UUID.randomUUID())
                .build();
    }

}
