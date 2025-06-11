package com.team_3.nursing_care.domain.care_log.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareItemDto {
    private Integer careItemId;
    private Integer requiredMinutes;
}