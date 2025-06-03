package com.team_3.nursing_care.domain.schedule.service;


import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.schedule.constant.PaymentType;
import com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus;
import com.team_3.nursing_care.domain.schedule.dto.request.*;
import com.team_3.nursing_care.domain.schedule.dto.response.*;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto) {
        Member caregiver = memberRepository.findById(createScheduleRequestDto.getCaregiverId())
                .orElseThrow(() -> new IllegalArgumentException("요양보호사를 찾을 수 없습니다."));

        Member patient = memberRepository.findById(createScheduleRequestDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("환자(Member)를 찾을 수 없습니다."));

        scheduleRepository.save(createScheduleRequestDto.toEntity(caregiver, patient.getAddress()));
    }

    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalStateException("존재 하지 않는 스케줄 ID 입니다."));
        schedule.updateIsDelete(true);
    }

    @Transactional
    @Override
    public void editSchedule(Long scheduleId, UpdateScheduleRequestDto updateScheduleRequestDto) {
        Member caregiver = memberRepository.findById(updateScheduleRequestDto.getCaregiverId())
                .orElseThrow(() -> new IllegalArgumentException("요양보호사를 찾을 수 없습니다."));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalStateException("존재 하지 않는 스케줄 ID 입니다."));

        Member patient = memberRepository.findById(updateScheduleRequestDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("환자(Member)를 찾을 수 없습니다."));
        scheduleRepository.save(buildUpdateSchedule(scheduleId,
                updateScheduleRequestDto,
                caregiver,
                schedule.getStatus(),
                patient.getAddress()));

    }

    @Override
    public ScheduleDayCaregiverListResDto getScheduleDayCaregiverList(Long caregiverId,
                                                                      ReadScheduleDayCaregiverReqDto readScheduleDayCaregiverReqDto) {

        List<ScheduleDayCaregiverResDto> scheduleDayCaregiverResDto = scheduleRepository.findByMemberIdAndScheduleDate(caregiverId, readScheduleDayCaregiverReqDto.getScheduleDate())
                .stream()
                .map(ScheduleDayCaregiverResDto::from)
                .toList();

        return ScheduleDayCaregiverListResDto.from(scheduleDayCaregiverResDto);
    }

    @Override
    public ScheduleMonthCaregiverListResDto getScheduleMonthCaregiverList(Long caregiverId, ReadScheduleMonthCaregiverReqDto dto) {

        LocalDate startOfMonth = LocalDate.of(dto.getYear(), dto.getMonth(), 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Schedule> schedules = scheduleRepository.findAllByMember_MemberIdAndIsDeletedFalse(caregiverId).stream()
                .filter(schedule ->
                        !(schedule.getEndDate().isBefore(startOfMonth) || schedule.getStartDate().isAfter(endOfMonth)))
                .toList();

        List<ScheduleMonthCaregiverResDto> scheduleMonth = schedules.stream()
                .map(schedule -> {
                    LocalDate effectiveStart = schedule.getStartDate().isBefore(startOfMonth) ? startOfMonth : schedule.getStartDate();
                    LocalDate effectiveEnd = schedule.getEndDate().isAfter(endOfMonth) ? endOfMonth : schedule.getEndDate();
                    return ScheduleMonthCaregiverResDto.builder()
                            .scheduleId(schedule.getScheduleId())
                            .startDate(effectiveStart)
                            .endDate(effectiveEnd)
                            .workDay(schedule.getWorkDay())
                            .build();
                })
                .toList();

        return ScheduleMonthCaregiverListResDto.from(scheduleMonth);
    }

    @Override
    public ScheduleWeekCaregiverListResDto getScheduleWeekCaregiverList(
            Long caregiverId, ReadScheduleWeekCaregiverReqDto readScheduleWeekCaregiverReqDto) {

        LocalDate startDate = readScheduleWeekCaregiverReqDto.getStartDate();
        LocalDate endDate = startDate.plusDays(6);

        List<ScheduleWeekCaregiverResDto> scheduleWeek = scheduleRepository
                .findAllByMember_MemberIdAndIsDeletedFalse(caregiverId).stream()
                .filter(schedule ->
                        !schedule.getEndDate().isBefore(startDate) &&
                                !schedule.getStartDate().isAfter(endDate))
                .flatMap(schedule -> {
                    LocalDate effectiveStart = schedule.getStartDate().isBefore(startDate) ? startDate : schedule.getStartDate();
                    LocalDate effectiveEnd = schedule.getEndDate().isAfter(endDate) ? endDate : schedule.getEndDate();

                    return effectiveStart.datesUntil(effectiveEnd.plusDays(1))
                            .filter(date -> (schedule.getWorkDay() & (1 << (date.getDayOfWeek().getValue() % 7))) != 0)
                            .map(date -> ScheduleWeekCaregiverResDto.builder()
                                    .scheduleId(schedule.getScheduleId())
                                    .scheduleDate(date)
                                    .startTime(schedule.getStartTime())
                                    .endTime(schedule.getEndTime())
                                    .patientAddress(schedule.getPatientAddress())
                                    .build());
                })
                .toList();

        return ScheduleWeekCaregiverListResDto.from(scheduleWeek);
    }

    private Schedule buildUpdateSchedule(
            Long scheduleId,
            UpdateScheduleRequestDto updateScheduleRequestDto,
            Member caregiver,
            ScheduleStatus status,
            String patientAddress
            ) {
        return Schedule.builder()
                .scheduleId(scheduleId)
                .patient(updateScheduleRequestDto.getPatientName())
                .member(caregiver)
                .startDate(updateScheduleRequestDto.getStartDate())
                .endDate(updateScheduleRequestDto.getEndDate())
                .startTime(updateScheduleRequestDto.getStartTime())
                .endTime(updateScheduleRequestDto.getEndTime())
                .paymentType(PaymentType.from(updateScheduleRequestDto.getPaymentType()))
                .workDay(updateScheduleRequestDto.getWorkDay())
                .paymentForHour(updateScheduleRequestDto.getPaymentForHour())
                .isFamily(updateScheduleRequestDto.getIsFamily())
                .status(status)
                .patientAddress(patientAddress)
                .build();
    }
}