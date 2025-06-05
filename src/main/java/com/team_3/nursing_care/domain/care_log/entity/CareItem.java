package com.team_3.nursing_care.domain.care_log.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.care_log.constant.CareItemType;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareItemDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "care_item")
@Builder
public class CareItem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CareItemType type;

    public static CareItem create(ReqCreateCareItemDto reqCreateCareItemDto) {
        return CareItem.builder()
                .name(reqCreateCareItemDto.getCareItemName())
                .type(reqCreateCareItemDto.getCareItemType())
                .build();
    }

    public CareItem update(ReqUpdateCareItemDto reqUpdateCareItemDto) {
        this.name = reqUpdateCareItemDto.getCareItemName() != null ? reqUpdateCareItemDto.getCareItemName() : name;
        this.type = reqUpdateCareItemDto.getCareItemType() != null ? reqUpdateCareItemDto.getCareItemType() : type;
        return this;
    }
}
