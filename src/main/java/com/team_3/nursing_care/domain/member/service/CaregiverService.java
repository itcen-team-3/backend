package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CaregiverInfoRequestDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CaregiverService {
    List<Member> getMembers(Long companyId, Role role);

    Member addCaregiver(CaregiverInfoRequestDto caregiverInfoRequestDto, Role role, MultipartFile profileImage);
}
