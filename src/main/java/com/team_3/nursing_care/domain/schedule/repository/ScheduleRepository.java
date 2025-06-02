package com.team_3.nursing_care.domain.schedule.repository;

import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where :scheduleDate between s.startDate and s.endDate AND s.member.memberId = :memberId")
    List<Schedule> findByMemberIdAndScheduleDate(Long memberId, LocalDate scheduleDate);
//
//    @Query("SELECT s FROM Schedule s WHERE :scheduleDate BETWEEN s.startDate AND s.endDate AND s.caregiver.id = :memberId")
//    List<Schedule> findByMemberIdAndScheduleDate(@Param("memberId") Long memberId,
//                                                 @Param("scheduleDate") LocalDate scheduleDate);


}
