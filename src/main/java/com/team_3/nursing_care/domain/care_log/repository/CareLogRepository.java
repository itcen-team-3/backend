package com.team_3.nursing_care.domain.care_log.repository;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {

    @Query("SELECT cl FROM CareLog cl WHERE cl.id = :id")
    @EntityGraph(attributePaths = {"careDetailList", "careLogImageList", "careGiver"})
    Optional<CareLog> findByIdJoinEntity(Long id);
}
