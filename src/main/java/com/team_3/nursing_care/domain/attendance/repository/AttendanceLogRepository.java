package com.team_3.nursing_care.domain.attendance.repository;

import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {

    List<AttendanceLog> findByMember_MemberIdAndCheckInStatusOrCheckOutStatus(Long memberId, CheckInStatus checkInStatus, CheckOutStatus checkOutStatus);

    List<AttendanceLog> findByMember_memberId(Long caregiverId);

    @Query("select al from AttendanceLog al " +
            "where al.member = :member " +
            "and al.patientId = :patientId " +
            "and al.checkIn IS NOT NULL " +
            "and al.checkIn > :startOfDay " +
            "and al.checkIn < :startOfNextDay")
    Optional<AttendanceLog> findByMemberAndCheckInDate(
            @Param("member") Member member,
            @Param("patientId") Long patientId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay
    );

    @Query("select al from AttendanceLog al " +
            "where al.member = :member " +
            "and al.checkIn IS NOT NULL " +
            "and al.patientId = :patientId " +
            "and al.checkOut IS NULL " +
            "and al.checkOut > :startOfDay " +
            "and al.checkOut < :startOfNextDay")
    Optional<AttendanceLog> findByMemberAndCheckOutDate(
            @Param("member") Member member,
            @Param("patientId") Long patientId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay
    );

    List<AttendanceLog> findAllByMember_MemberIdIn(List<Long> memberIds);

    Optional<AttendanceLog> findByMember_MemberIdAndCheckInBetween(Long memberId, LocalDateTime start, LocalDateTime end);

    @Query("select al from AttendanceLog al " +
            "join fetch al.member " +
            "where al.checkInStatus IS NULL " +
            "and al.checkOutStatus IS NULL " +
            "and al.isDeleted is false")
    List<AttendanceLog> findAllForSchedule();

    @Query("select al from AttendanceLog al " +
            "join fetch al.member " +
            "where al.member = :member " +
            "and al.patientId = :patientId " +
            "and al.checkIn > :startDate " +
            "and al.checkOut < :endDate")
    List<AttendanceLog> findLogForSalary(Member member, String patient, LocalDateTime startDate, LocalDateTime endDate);
}
