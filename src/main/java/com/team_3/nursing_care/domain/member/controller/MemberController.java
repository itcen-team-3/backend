package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/caregiver-name-list/{companyId}")
    public ResponseEntity<?> getCaregiverNameList(@PathVariable Long companyId) {

        return null;
    }

}
