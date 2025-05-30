package com.team_3.nursing_care.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsException extends RuntimeException {
    private int code;
    private ResultMessage result;
    private String message;

    public SmsException(int code, String message) {
        this.code = code;
        this.result = ResultMessage.Error;
        this.message = message;
    }

}
