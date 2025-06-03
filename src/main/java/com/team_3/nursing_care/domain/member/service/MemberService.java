package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.ReqLoginDto;
import com.team_3.nursing_care.domain.member.dto.request.ReqSignUpDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiversNameListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientsNameListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.ResLoginDto;

public interface MemberService {

    CaregiversNameListResponseDto getCaregiversName(Long companyId, Role role);

    PatientsNameListResponseDto getPatientsName(Long companyId, Role role);

    void signup(ReqSignUpDto reqSignUpDto);

    ResLoginDto login(ReqLoginDto reqLoginDto);
}
