package com.team_3.nursing_care.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResLoginDto {

    private String accessToken;
    private String refreshToken;

    public static ResLoginDto create(String accessToken, String refreshToken) {
        return ResLoginDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
