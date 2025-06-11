package com.team_3.nursing_care.domain.salary.repository;

import com.team_3.nursing_care.domain.salary.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByScheduleIdIn(Collection<Long> scheduleIds);
}
