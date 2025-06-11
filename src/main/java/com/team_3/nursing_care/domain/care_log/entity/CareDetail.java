package com.team_3.nursing_care.domain.care_log.entity;

import com.team_3.nursing_care.domain.care_log.dto.request.CareItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "care_detail")
public class CareDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_log_id", nullable = false)
    private CareLog careLog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "care_item_id", nullable = false)
    private CareItem careItem;

    @Column(nullable = false)
    private Integer requiredMinutes;

    public static CareDetail create(CareItemDto careItemDto, CareItem careItem) {
        return CareDetail.builder()
                .careItem(careItem)
                .requiredMinutes(careItemDto.getRequiredMinutes())
                .build();
    }

    public void connectCareLog(CareLog careLog) {
        this.careLog = careLog;
    }
}
