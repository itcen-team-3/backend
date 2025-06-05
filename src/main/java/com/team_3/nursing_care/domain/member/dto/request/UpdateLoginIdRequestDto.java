package com.team_3.nursing_care.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLoginIdRequestDto {

    @NotBlank(message = "loginNewId 는 필수 항목입니다.")
    private String loginNewId;

}
