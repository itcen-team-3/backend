package com.team_3.nursing_care.domain.care_log.dto.request;

import com.team_3.nursing_care.domain.care_log.constant.CareItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCreateCareItemDto {

    private String careItemName;
    private CareItemType careItemType;

}
