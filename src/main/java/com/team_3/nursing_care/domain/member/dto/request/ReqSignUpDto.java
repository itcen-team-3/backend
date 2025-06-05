package com.team_3.nursing_care.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqSignUpDto {

    @NotBlank(message = "loginId 는 필수 항목입니다.")
    private String loginId;
    @NotBlank(message = "loginPw 는 필수 항목입니다.")
    private String loginPw;
    @NotBlank(message = "loginPwConfirm 는 필수 항목입니다.")
    private String loginPwConfirm;
    @NotBlank(message = "representativeName 는 필수 항목입니다.")
    private String representativeName;
    @NotNull(message = "생년월일은 필수 항목입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotBlank(message = "companyName 는 필수 항목입니다.")
    private String companyName;
    @NotBlank(message = "companyAddress 는 필수 항목입니다.")
    private String companyAddress;
    @NotBlank(message = "email 는 필수 항목입니다.")
    private String email;
    @NotNull    (message = "businessRegistrationFile 는 필수 항목입니다.")
    private MultipartFile businessRegistrationFile;
    @NotBlank(message = "businessRegistrationNumber 는 필수 항목입니다.")
    private String businessRegistrationNumber;
    @NotBlank(message = "개업연월일은 는 필수 항목입니다.")
    private String openingDate;

    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리의 숫자로만 입력해주세요.")
    private String phoneNumber;

}
