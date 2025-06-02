//package com.team_3.nursing_care.domain.sms.controller;
//
//import com.team_3.nursing_care.common.response.ResponseDto;
//import com.team_3.nursing_care.domain.sms.dto.ReqSmsDto;
//import com.team_3.nursing_care.domain.sms.service.SmsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
//import static software.amazon.awssdk.http.HttpStatusCode.OK;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/sms")
//public class SmsController {
//
//    private final SmsService smsService;
//
//    @PostMapping("/send-one")
//    public ResponseEntity<?> sendOne(@RequestBody ReqSmsDto dto) {
//        return ResponseEntity.ok(new ResponseDto<>(OK, Success, smsService.sendSms(dto)));
//    }
//
//    @GetMapping("/get-balance")
//    public ResponseEntity<?> getBalance() {
//        return ResponseEntity.ok(new ResponseDto<>(OK, Success, smsService.getBalance()));
//    }
//
//    @GetMapping("/exist-cache")
//    public ResponseEntity<?> existCache(@RequestBody String toPhoneNumber) {
//        return ResponseEntity.ok(new ResponseDto<>(OK, Success, smsService.existCache(toPhoneNumber)));
//    }
//
//}
