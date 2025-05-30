package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.dto.response.CaregiversNameResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;

import java.util.List;

public interface MemberService {
    List<Member> getMembers(Long companyId, Role role);
    List<CaregiversNameResponseDto> getCaregiversName(Long companyId, Role role);
}
