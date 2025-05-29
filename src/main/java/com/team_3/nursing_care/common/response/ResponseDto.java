package com.team_3.nursing_care.common.response;

import com.team_3.nursing_care.common.exception.ResultMessage;
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

