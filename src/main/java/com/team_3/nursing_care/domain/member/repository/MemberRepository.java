package com.team_3.nursing_care.domain.member.repository;

import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByCompany_CompanyIdAndRoleAndIsDeletedFalse(Long companyId, Role role, Pageable pageable);

    Page<Member> findByCompany_CompanyIdAndRoleAndMemberNameContainingAndIsDeletedFalse(Long companyId, Role role, String memberName, Pageable pageable);

    List<Member> findByCompany_CompanyIdAndRole(Long companyId, Role role);

    List<Member> findByCompany_CompanyIdAndRoleAndLoginIdIsNull(Long comapnyId, Role role);

    Optional<Member> findByMemberIdAndRole(Long memberId, Role role);

    Optional<Member> findByLoginId(String loginId);

    @Query("select m from Member m join fetch m.patientInfo where m.patientInfo.nfcUuid = :nfcUuid")
    Optional<Member> findPatientByNfcUuid(UUID nfcUuid);


}
