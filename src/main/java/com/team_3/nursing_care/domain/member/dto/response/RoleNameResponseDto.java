package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleNameResponseDto {

    private String name;
    private String displayName;

    public static RoleNameResponseDto from(Role role) {
        return new RoleNameResponseDto(role.name(), role.getDisplayName());
    }


}
