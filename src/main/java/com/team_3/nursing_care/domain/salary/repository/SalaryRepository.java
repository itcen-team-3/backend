package com.team_3.nursing_care.domain.salary.repository;

import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.salary.entity.Salary;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByScheduleIn(Collection<Schedule> scheduleList);


    @Query("select s from Salary s " +
            "join fetch Member m " +
            "on s.careGiverId = m.memberId " +
            "where m.isDeleted = false " +
            "and m.company = :company " +
            "and year(s.createDate) = :year " +
            "and month(s.createDate) = :month")
    List<Salary> findByCompany(
            @Param("company") Company company,
            @Param("year") int year,
            @Param("month") int month
    );
}
