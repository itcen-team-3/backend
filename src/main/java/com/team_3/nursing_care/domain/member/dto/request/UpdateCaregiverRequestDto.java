package com.team_3.nursing_care.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCaregiverRequestDto {

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

    @NotBlank(message = "자격증 번호는 필수값입니다.")
    private String certificateNumber;

    @NotNull(message = "경력은 필수값입니다.")
    private short career;

    private String description;

    @Builder
    public UpdateCaregiverRequestDto(
            String name,
            LocalDate birthDate,
            String phoneNumber,
            String address,
            String certificateNumber,
            short career,
            String description) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.certificateNumber = certificateNumber;
        this.career = career;
        this.description = description;
    }

}