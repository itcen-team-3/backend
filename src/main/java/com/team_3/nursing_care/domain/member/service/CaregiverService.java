package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverListResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CaregiverService {

    Page<CaregiverListResponseDto> getCaregiverList(Long companyId, String searchName, Pageable pageable);

    Member addCaregiver(CreateCaregiverRequestDto createCaregiverRequestDto, Role role, MultipartFile profileImage, Member admin);

}
