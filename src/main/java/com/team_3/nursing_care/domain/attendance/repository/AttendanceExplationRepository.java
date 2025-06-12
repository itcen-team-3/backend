package com.team_3.nursing_care.domain.attendance.repository;

import com.team_3.nursing_care.domain.attendance.entity.AttendanceExplation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceExplationRepository extends JpaRepository<AttendanceExplation, Long> {

    @Query("SELECT ae FROM AttendanceExplation ae " +
            "JOIN FETCH ae.attendanceLog al " +
            "JOIN FETCH al.member m " +
            "WHERE m.memberId IN :caregiverIds")
    List<AttendanceExplation> findByCaregiverIds(@Param("caregiverIds") List<Long> caregiverIds);


    @Query("SELECT ae FROM AttendanceExplation ae " +
            "WHERE ae.caregiverId= :caregiverId")
    List<AttendanceExplation> findAllByCaregiverId(@Param("caregiverId") Long caregiverId);


}
