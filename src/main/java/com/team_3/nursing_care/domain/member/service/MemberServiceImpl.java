package com.team_3.nursing_care.domain.member.service;

import com.team_3.nursing_care.common.exception.MemberException;
import com.team_3.nursing_care.common.proxy.S3Service;
import com.team_3.nursing_care.common.util.JwtUtil;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.ReqLoginDto;
import com.team_3.nursing_care.domain.member.dto.request.ReqSignUpDto;
import com.team_3.nursing_care.domain.member.dto.response.*;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.proxy.dto.ReqBusinessAuthenticityDto;
import com.team_3.nursing_care.domain.member.proxy.service.BusinessAuthenticityService;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final BusinessAuthenticityService businessAuthenticityService;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final JwtUtil jwtUtil;

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

    @Override
    @Transactional
    public void signup(ReqSignUpDto reqSignUpDto) {
        Member member = memberRepository.findByLoginId(reqSignUpDto.getLoginId()).orElse(null);
        if (member != null)
            throw new MemberException(HttpStatusCode.BAD_REQUEST, "duplicate login id: " + reqSignUpDto.getLoginId());
        if (!reqSignUpDto.getLoginPw().equals(reqSignUpDto.getLoginPwConfirm()))
            throw new MemberException(HttpStatusCode.BAD_REQUEST, "not match login password Confirm");

        String s3Key = s3Service.uploadBusinessRegistrationFile(reqSignUpDto.getBusinessRegistrationFile());
        String fileUrl = s3Service.getFileUrl(s3Key);

        businessAuthenticityService.validate(new ReqBusinessAuthenticityDto(
                        reqSignUpDto.getBusinessRegistrationNumber(),
                        reqSignUpDto.getOpeningDate(),
                        reqSignUpDto.getRepresentativeName()))
                .block();

        Company company = companyRepository.save(Company.create(reqSignUpDto, fileUrl));

        String encodedPw = passwordEncoder.encode(reqSignUpDto.getLoginPw());
        Member admin = Member.createAdmin(reqSignUpDto, encodedPw);

        admin.connectCompany(company);
        memberRepository.save(admin);
    }

    @Override
    public ResLoginDto login(ReqLoginDto reqLoginDto) {
        Member member = memberRepository.findByLoginId(reqLoginDto.getLoginId()).orElseThrow(() -> new MemberException(HttpStatusCode.NOT_FOUND, "not found member by login id: " + reqLoginDto.getLoginId()));
        if (!passwordEncoder.matches(reqLoginDto.getLoginPw(), member.getLoginPw()))
            throw new MemberException(HttpStatusCode.BAD_REQUEST, "not match login password");

        Set<Role> roles = Set.of(member.getRole());
        String accessToken = jwtUtil.createAccessToken(member.getMemberId(), member.getCompany().getCompanyId(), roles);
        String refreshToken = jwtUtil.createRefreshToken(member.getMemberId(), roles);

        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return ResLoginDto.create(accessToken, refreshToken);
    }

}
