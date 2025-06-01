package com.team_3.nursing_care.domain.member.exception;

import com.team_3.nursing_care.common.exception.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private ResultMessage code;
    private String message;
}
