package com.team_3.nursing_care.domain.schedule.repository;

import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where :scheduleDate between s.startDate and s.endDate AND s.member.memberId = :memberId")
    List<Schedule> findByMemberIdAndScheduleDate(Long memberId, LocalDate scheduleDate);

    List<Schedule> findAllByMember_MemberIdAndIsDeletedFalse(Long memberId);
}
