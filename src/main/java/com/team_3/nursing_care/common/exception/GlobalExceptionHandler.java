package com.team_3.nursing_care.common.exception;

import com.team_3.nursing_care.common.response.ResponseDto;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> handleValidation(MethodArgumentNotValidException ex){
        log.warn("❗ Validation error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseDto<>(HttpStatusCode.BAD_REQUEST, ResultMessage.Error, ex.getMessage()));
    }

    @ExceptionHandler(SmsException.class)
    public ResponseEntity<ResponseDto<?>> handleSmsException(SmsException ex){
        log.warn("❗ SmsException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }

    @ExceptionHandler(CareLogException.class)
    public ResponseEntity<ResponseDto<?>> handleCareLogException(CareLogException ex){
        log.warn("❗ CareLogException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ResponseDto<?>> handleMemberException(MemberException ex){
        log.warn("❗ MemberException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }

    @ExceptionHandler(GeofenceException.class)
    public ResponseEntity<ResponseDto<?>> handleGeofenceException(GeofenceException ex){
        log.warn("❗ GeofenceException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ResponseDto<?>> handleUnexpectedTypeException(UnexpectedTypeException ex){
        log.warn("❗ UnexpectedTypeException error: {}",ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseDto<>(HttpStatusCode.BAD_REQUEST, ResultMessage.Error, ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleUsernameNotFoundException(UsernameNotFoundException ex){
        log.warn("❗ UsernameNotFoundException error: {}",ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseDto<>(HttpStatusCode.BAD_REQUEST, ResultMessage.Error, ex.getMessage()));
    }

    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<ResponseDto<?>> handleScheduleException(ScheduleException ex){
        log.warn("❗ ScheduleException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }

    @ExceptionHandler(NfcException.class)
    public ResponseEntity<ResponseDto<?>> handleNfcException(NfcException ex){
        log.warn("❗ NfcException error: {}",ex.getMessage());

        return ResponseEntity.status(ex.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.toResponseDto());
    }

}
