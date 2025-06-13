package com.team_3.nursing_care.domain.nfc.service;

import com.team_3.nursing_care.common.exception.NfcException;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NfcServiceImpl implements NfcService {

    private final AttendanceLogRepository attendanceLogRepository;
    private final MemberRepository memberRepository;


    // 하루 2탕 근무를 할 경우를 대비해, UUID 가 일치해야 하는 조건 추가 ( 같은 집을 2번 갈 수는 없음 )

    @Override
    @Transactional
    public void startWork(UUID uuid, CustomUserDetails userDetails) {
        Member patient = memberRepository.findPatientByNfcUuid(uuid).orElseThrow(() -> new NfcException(HttpStatusCode.NOT_FOUND, "patient not found by nfc uuid: " + uuid));
        Member careGiver = memberRepository.findById(userDetails.getMemberId()).orElseThrow(() -> new NfcException(HttpStatusCode.NOT_FOUND, "Member not found"));

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        AttendanceLog existed = attendanceLogRepository.findByMemberAndCheckInDate(careGiver, patient.getMemberId(), startOfToday.getYear(), startOfToday.getMonthValue(), startOfToday.getDayOfMonth()).orElse(null);
        if (existed != null) throw new NfcException(HttpStatusCode.BAD_REQUEST, "today already startWork");

        attendanceLogRepository.save(AttendanceLog.create(careGiver, patient.getMemberId()));
    }

    @Override
    @Transactional
    public void endWork(UUID uuid, CustomUserDetails userDetails) {
        Member patient = memberRepository.findPatientByNfcUuid(uuid).orElseThrow(() -> new NfcException(HttpStatusCode.NOT_FOUND, "patient not found by nfc uuid: " + uuid));
        Member careGiver = memberRepository.findById(userDetails.getMemberId()).orElseThrow(() -> new NfcException(HttpStatusCode.NOT_FOUND, "Member not found"));

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime startOfTomorrow = startOfToday.plusDays(1);
        AttendanceLog attendanceLog = attendanceLogRepository.findByMemberAndCheckOutDate(careGiver, patient.getMemberId(), startOfToday.getYear(), startOfToday.getMonthValue(), startOfToday.getDayOfMonth()).orElseThrow(() -> new NfcException(HttpStatusCode.NOT_FOUND, "today startWork record is not found"));

        attendanceLogRepository.save(attendanceLog.update());
    }
}
