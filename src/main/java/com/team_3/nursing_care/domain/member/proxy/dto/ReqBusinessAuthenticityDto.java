package com.team_3.nursing_care.domain.member.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqBusinessAuthenticityDto {
    private String b_no;
    private String start_dt;
    private String p_nm;
}
