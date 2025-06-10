package com.team_3.nursing_care.domain.schedule.repository;

import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where :scheduleDate between s.startDate and s.endDate AND s.member.memberId = :memberId")
    List<Schedule> findByMemberIdAndScheduleDate(Long memberId, LocalDate scheduleDate);

    List<Schedule> findAllByMember_MemberIdAndIsDeletedFalse(Long memberId);

    List<Schedule> findAllByPatientIdAndIsDeletedFalse(Long patientId);

    Optional<Schedule> findByMember(Member member);

    @Query("select s from Schedule s " +
            "where s.member = :member " +
            "and s.patientId = :patientId " +
            "and s.startDate <= :createDate " +
            "and s.endDate >= :createDate")
    Optional<Schedule> findScheduleByCareLog(
            @Param("member") Member member,
            @Param("patientId") Long patientId,
            @Param("createDate") LocalDate createDate
    );
}
