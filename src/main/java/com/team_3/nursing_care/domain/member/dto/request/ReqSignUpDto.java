package com.team_3.nursing_care.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqSignUpDto {

    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPw;
    @NotBlank
    private String loginPwConfirm;
    @NotBlank
    private String representativeName;
    @NotBlank
    private LocalDate birthDate;
    @NotBlank
    private String companyName;
    @NotBlank
    private String companyAddress;
    @NotBlank
    private String email;
    @NotBlank
    private MultipartFile businessRegistrationFile;
    @NotBlank
    private String businessRegistrationNumber;
    @NotBlank
    private String openingDate;

    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리의 숫자로만 입력해주세요.")
    private String phoneNumber;

}
