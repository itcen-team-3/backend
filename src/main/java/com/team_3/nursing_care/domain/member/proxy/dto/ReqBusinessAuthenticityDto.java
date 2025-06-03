package com.team_3.nursing_care.domain.member.proxy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqBusinessAuthenticityDto {

    @NotBlank(message = "사업자번호 (-빼고)")
    private String b_no;
    @NotBlank(message = "개업연월일 (-빼고)")
    private String start_dt;
    @NotBlank(message = "대표자명")
    private String p_nm;
}
