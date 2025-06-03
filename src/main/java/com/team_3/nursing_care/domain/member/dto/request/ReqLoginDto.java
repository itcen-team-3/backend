package com.team_3.nursing_care.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLoginDto {

    @NotBlank(message = "로그인 아이디는 필수 항목입니다.")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String loginPw;
}
