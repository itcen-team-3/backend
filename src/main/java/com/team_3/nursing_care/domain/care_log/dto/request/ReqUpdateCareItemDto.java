package com.team_3.nursing_care.domain.care_log.dto.request;

import com.team_3.nursing_care.domain.care_log.constant.CareItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdateCareItemDto {

    private String careItemName;
    private CareItemType careItemType;

}
