package com.team_3.nursing_care.domain.member.repository;

import com.team_3.nursing_care.domain.member.entity.PatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {
}
