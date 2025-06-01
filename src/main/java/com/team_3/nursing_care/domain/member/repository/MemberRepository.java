package com.team_3.nursing_care.domain.member.repository;

import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByCompany_CompanyIdAndRole(Long companyId, Role role);

}
