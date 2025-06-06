package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateAccountResponseDto {

    private Long memberId;
    private String loginId;

    public static UpdateAccountResponseDto from(Member member) {
        return new UpdateAccountResponseDto(member.getMemberId(), member.getLoginId());
    }

}
