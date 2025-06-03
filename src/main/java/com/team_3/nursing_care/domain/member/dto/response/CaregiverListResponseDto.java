package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CaregiverListResponseDto {

    private Long caregiverId;
    private String name;
    private String phoneNumber;
    private boolean isDeleted;

    public static CaregiverListResponseDto from(Member member) {
        return new CaregiverListResponseDto(
                member.getMemberId(),
                member.getMemberName(),
                member.getPhoneNumber(),
                member.getIsDeleted()
        );
    }

}
