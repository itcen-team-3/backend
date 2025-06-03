package com.team_3.nursing_care.common.exception;

import com.team_3.nursing_care.common.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex){
        log.warn("❗ Validation error: {}",ex.getMessage());

        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_ERROR",errorMsg));
    }

    @ExceptionHandler(SmsException.class)
    public ResponseEntity<ResponseDto<?>> handleSmsException(SmsException ex){
        log.warn("❗ SmsException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }
}
