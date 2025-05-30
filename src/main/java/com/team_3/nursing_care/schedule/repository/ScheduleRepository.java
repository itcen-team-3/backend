package com.team_3.nursing_care.schedule.repository;

import com.team_3.nursing_care.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
