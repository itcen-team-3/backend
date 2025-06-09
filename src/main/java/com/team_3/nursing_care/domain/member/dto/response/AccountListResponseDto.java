package com.team_3.nursing_care.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountListResponseDto {

    private Long memberId;
    private String loginId;
    private String name;
    private String role;
    private String patientName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastLoginAt;

    public static AccountListResponseDto create(Member member) {
        String guardianName = null;
        if (member.getPatientInfo() != null) {
            guardianName = member.getPatientInfo().getGuardianName();
        }

        boolean hasGuardian = guardianName != null && !guardianName.isBlank();

        return AccountListResponseDto.builder()
                .memberId(member.getMemberId())
                .loginId(member.getLoginId())
                .name(hasGuardian ? guardianName : member.getMemberName())
                .role(member.getRole().getDisplayName())
                .patientName(hasGuardian ? member.getMemberName() : null)
                .lastLoginAt(member.getLastLoginAt())
                .build();
    }

}
