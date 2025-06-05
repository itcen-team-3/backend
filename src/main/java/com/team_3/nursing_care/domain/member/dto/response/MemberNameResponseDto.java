package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberNameResponseDto {

    Long memberId;
    String name;

    public static MemberNameResponseDto from(Member member){
        return new MemberNameResponseDto(member.getMemberId(), member.getMemberName());
    }

}
