package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.domain.member.dto.response.CaregiversNameListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.CaregiversNameResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientsNameListResponseDto;
import com.team_3.nursing_care.domain.member.dto.response.PatientsNameResponseDto;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public CaregiversNameListResponseDto getCaregiversName(Long companyId, Role role) {

        List<CaregiversNameResponseDto> caregiverList = memberRepository.findByCompany_CompanyIdAndRole(companyId, role)
                .stream()
                .map(CaregiversNameResponseDto::from)
                .toList();

        return CaregiversNameListResponseDto.from(caregiverList);
    }

    @Override
    public PatientsNameListResponseDto getPatientsName(Long companyId, Role role) {

        List<PatientsNameResponseDto> patientsList = memberRepository.findByCompany_CompanyIdAndRole(companyId, role)
                .stream()
                .map(PatientsNameResponseDto::from)
                .toList();

        return PatientsNameListResponseDto.from(patientsList);
    }

}
