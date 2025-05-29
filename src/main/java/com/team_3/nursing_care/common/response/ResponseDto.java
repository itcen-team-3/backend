package com.team_3.nursing_care.common.response;

import com.team_3.nursing_care.common.exception.ResultMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private int code;
    private ResultMessage message;
    private T data;

}

