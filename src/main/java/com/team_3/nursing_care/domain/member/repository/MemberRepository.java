package com.team_3.nursing_care.domain.member.repository;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByCompany_CompanyIdAndRoleAndIsDeletedFalse(Long companyId, Role role, Pageable pageable);

    Page<Member> findByCompany_CompanyIdAndRoleAndMemberNameContainingAndIsDeletedFalse(Long companyId, Role role, String memberName, Pageable pageable);

    List<Member> findByCompany_CompanyIdAndRole(Long companyId, Role role);

    Optional<Member> findByMemberIdAndRole(Long memberId, Role role);

    Optional<Member> findByLoginId(String loginId);
}
