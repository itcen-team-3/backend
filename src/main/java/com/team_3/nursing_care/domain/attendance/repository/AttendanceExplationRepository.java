package com.team_3.nursing_care.domain.attendance.repository;

import com.team_3.nursing_care.domain.attendance.entity.AttendanceExplation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceExplationRepository extends JpaRepository<AttendanceExplation, Long> {
}
