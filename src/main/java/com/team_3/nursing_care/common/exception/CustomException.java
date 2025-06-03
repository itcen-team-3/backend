package com.team_3.nursing_care.common.exception;

import com.team_3.nursing_care.common.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private int code;
    private ResultMessage result;
    private String message;

    public CustomException(int code, String message) {
        this.code = code;
        this.result = ResultMessage.Error;
        this.message = message;
    }

    public ResponseDto<?> toResponseDto() {
        return new ResponseDto<>(code, result, message);
    }
}
