package com.team_3.nursing_care.common.security.user;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberUserDetails extends CustomUserDetails {

    private Long memberId;
    private Long companyId;

    private String memberName;
    private String refreshToken;
    private Boolean isDelete;

    public static MemberUserDetails of(Member member) {
        MemberUserDetails memberUserDetails = MemberUserDetails.builder()
                .memberId(member.getMemberId())
                .companyId(member.getCompany().getCompanyId())
                .memberName(member.getMemberName())
                .refreshToken(member.getRefreshToken())
                .build();

        memberUserDetails.roles = Set.of(member.getRole());
        return memberUserDetails;
    }
}
