package com.team_3.nursing_care.domain.care_log.repository;

import com.team_3.nursing_care.domain.care_log.entity.CareItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CareItemRepository extends JpaRepository<CareItem, Integer> {
    List<CareItem> findAllByIsDeletedFalse();

    Optional<CareItem> findByIdAndIsDeletedFalse(Integer id);

    List<CareItem> findByIdIn(List<Integer> list);
}
