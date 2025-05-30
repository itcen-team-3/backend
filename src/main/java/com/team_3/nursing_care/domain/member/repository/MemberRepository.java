package com.team_3.nursing_care.domain.member.repository;

import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.constant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByCompany_CompanyIdAndRole(Long companyId, Role role);
    Optional<Member> findByCompanyIdAndRole(Long companyId, Role role);
}
