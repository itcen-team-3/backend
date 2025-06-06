package com.team_3.nursing_care.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAccountRequestDto {

    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPw;

}
