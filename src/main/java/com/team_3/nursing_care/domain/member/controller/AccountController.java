package com.team_3.nursing_care.domain.member.controller;

import com.team_3.nursing_care.common.response.PageResponseDto;
import com.team_3.nursing_care.common.response.ResponseDto;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.member.dto.request.CreateAccountRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateLoginIdRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateLoginPwRequestDto;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import com.team_3.nursing_care.domain.member.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.team_3.nursing_care.common.exception.ResultMessage.Success;
import static software.amazon.awssdk.http.HttpStatusCode.OK;

@RestController
@RequestMapping("/api/v1/member/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAccountList(@RequestParam(name = "searchName", required = false) String searchName,
                                            @RequestParam(name = "searchRole", required = false) String searchRole,
                                            @AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<AccountListResponseDto> accountPage = accountService.getAccountList(searchName, searchRole, userDetails, pageable);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, new PageResponseDto<>(accountPage)));

    }

    @GetMapping("/id/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getId(@PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(new ResponseDto<>(OK, Success, accountService.getLoginId(memberId)));
    }

    @PostMapping("/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createAccount(@PathVariable("memberId") Long memberId, @Valid @RequestBody CreateAccountRequestDto dto) {
        accountService.addAccount(memberId, dto);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "계정이 정상적으로 등록 되었습니다."));
    }

    @PatchMapping("/id/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateLoginId(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateLoginIdRequestDto updateLoginIdRequestDto) {
        accountService.updateLoginId(memberId, updateLoginIdRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "계정 아이디가 정상적으로 변경 되었습니다."));
    }

    @PatchMapping("/pw/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateLoginPw(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateLoginPwRequestDto updateLoginPwRequestDto) {
        accountService.updateLoginPw(memberId, updateLoginPwRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(OK, Success, "계정 비밀번호가 정상적으로 변경 되었습니다."));
    }


}
