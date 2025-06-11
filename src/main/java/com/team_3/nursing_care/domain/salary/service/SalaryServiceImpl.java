package com.team_3.nursing_care.domain.salary.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.CompanyRepository;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.salary.dto.response.SalaryScheduleResDto;
import com.team_3.nursing_care.domain.salary.entity.Salary;
import com.team_3.nursing_care.domain.salary.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SalaryScheduleResDto> getSalarySchedule(int year, int month, CustomUserDetails userDetails) {
        Company company = companyRepository.findById(userDetails.getCompanyId()).orElseThrow(() -> new RuntimeException("Company not found"));
        List<Salary> salaryList = salaryRepository.findByCompany(company, year, month);


        return salaryList.stream().map(salary -> {
            Member member = memberRepository.findByMemberId(salary.getCareGiverId());
            return SalaryScheduleResDto.create(salary, member, year, month);
        }).toList();
    }
}
