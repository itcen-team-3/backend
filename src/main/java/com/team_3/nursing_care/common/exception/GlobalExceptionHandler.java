package com.team_3.nursing_care.domain.member.exception;

import com.team_3.nursing_care.common.exception.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<ErrorResponse> handleCompanyNotFound(CompanyException ex){
        log.warn("유효성 검사 실패: {}", ex.getMessage());
        ErrorResponse response = new ErrorResponse(ResultMessage.Error,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
