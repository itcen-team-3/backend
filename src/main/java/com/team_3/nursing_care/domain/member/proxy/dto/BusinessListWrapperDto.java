package com.team_3.nursing_care.domain.member.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessListWrapperDto {
    private List<BusinessAuthenticityDto> businesses;
}
