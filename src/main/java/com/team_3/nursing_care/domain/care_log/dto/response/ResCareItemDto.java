package com.team_3.nursing_care.domain.care_log.dto.response;

import com.team_3.nursing_care.domain.care_log.constant.CareItemType;
import com.team_3.nursing_care.domain.care_log.entity.CareItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCareItemDto {

    private Integer id;
    private String name;
    private CareItemType type;

    public static ResCareItemDto create(CareItem careItem) {
        return ResCareItemDto.builder()
                .id(careItem.getId())
                .name(careItem.getName())
                .type(careItem.getType())
                .build();
    }
}

