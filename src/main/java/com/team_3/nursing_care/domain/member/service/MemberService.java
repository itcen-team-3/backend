package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.dto.response.CaregiversNameListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientsNameListResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;

import java.util.List;

public interface MemberService {
    List<Member> getMembers(Long companyId, Role role);
    CaregiversNameListResponseDto getCaregiversName(Long companyId, Role role);
    PatientsNameListResponseDto getPatientsName(Long companyId, Role role);
}
