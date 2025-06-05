package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.CreateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverDetailResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiverListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.UpdateCaregiverResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface CaregiverService {

    Page<CaregiverListResponseDto> getCaregiverList(String searchName, CustomUserDetails userDetails, Pageable pageable);

    CaregiverDetailResponseDto getCaregiverDetail(Long caregiverId);

    UpdateCaregiverResponseDto getCaregiverInfo(Long caregiverId);

    Member addCaregiver(CreateCaregiverRequestDto createCaregiverRequestDto, Role role, MultipartFile profileImage, CustomUserDetails userDetails);

    void updateCaregiver(Long caregiverId, UpdateCaregiverRequestDto updateCaregiverRequestDto, MultipartFile profileImage);

    void deleteCaregiver(Long caregiverId);
}
