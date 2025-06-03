package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.exception.ResultMessage;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.domain.member.dto.request.ReqLoginDto;
import com.team_3.nursing_care.domain.member.dto.request.ReqSignUpDto;
import com.team_3.nursing_care.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public ResponseEntity<?> signup(@ModelAttribute @Valid ReqSignUpDto reqSignUpDto) {
        memberService.signup(reqSignUpDto);
        return ResponseEntity.ok(new ResponseDto<>(OK, ResultMessage.Success, null));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody ReqLoginDto reqLoginDto) {
        return ResponseEntity.ok(memberService.login(reqLoginDto));
    }

}
