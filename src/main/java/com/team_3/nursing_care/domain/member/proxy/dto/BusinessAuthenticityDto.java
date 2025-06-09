package com.team_3.nursing_care.domain.member.proxy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessAuthenticityDto {
    private String b_no;
    private String start_dt;
    private String p_nm;
    private String p_nm2;
    private String b_nm;
    private String corp_no;
    private String b_sector;
    private String b_type;
    private String b_adr;

    public static BusinessAuthenticityDto create(String b_no, String start_dt, String p_nm) {
        return BusinessAuthenticityDto.builder()
                .b_no(b_no)
                .start_dt(start_dt)
                .p_nm(p_nm)
                .p_nm2("")
                .b_nm("")
                .corp_no("")
                .b_sector("")
                .b_type("")
                .b_adr("")
                .build();
    }
}
