package com.team_3.nursing_care.domain.schedule.service;


import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.member.service.MemberService;
import com.team_3.nursing_care.domain.schedule.constant.PaymentType;
import com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus;
import com.team_3.nursing_care.domain.schedule.dto.request.*;
import com.team_3.nursing_care.domain.schedule.dto.response.*;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final MemberService memberService;

    @Transactional
    @Override
    public void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto) {
        Member caregiver = memberRepository.findById(createScheduleRequestDto.getCaregiverId())
                .orElseThrow(() -> new IllegalArgumentException("요양보호사를 찾을 수 없습니다."));

        Member patient = memberRepository.findById(createScheduleRequestDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("환자(Member)를 찾을 수 없습니다."));

        LocalDate start = createScheduleRequestDto.getStartDate();
        LocalDate end = createScheduleRequestDto.getEndDate();

        ScheduleStatus status;

        if (LocalDate.now().isBefore(start)) {
            status = ScheduleStatus.PLANNED;
        } else if (!LocalDate.now().isAfter(end)) {
            status = ScheduleStatus.ONGOING;
        } else {
            status = ScheduleStatus.COMPLETED;
        }

        scheduleRepository.save(createScheduleRequestDto.toEntity(caregiver, patient.getAddress(), status));
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
                                                                      LocalDate scheduleDate) {

        List<ScheduleDayCaregiverResDto> scheduleDayCaregiverResDto = scheduleRepository.findByMemberIdAndScheduleDate(caregiverId, scheduleDate)
                .stream()
                .map(ScheduleDayCaregiverResDto::from)
                .toList();

        return ScheduleDayCaregiverListResDto.from(scheduleDayCaregiverResDto);
    }

    @Override
    public ScheduleMonthCaregiverListResDto getScheduleMonthCaregiverList(Long caregiverId, String yearMonth) {

        String[] parts = yearMonth.split("-");

        LocalDate startOfMonth = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), 1);
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
    public ScheduleWeekCaregiverListResDto getScheduleWeekCaregiverList(Long caregiverId,
                                                                        LocalDate startDate) {
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

    @Override
    public ScheduleWeekAdminListResDto getScheduleWeekByAdmin(ReadScheduleWeekAdminReqDto readScheduleWeekAdminReqDto) {
        LocalDate startDate = readScheduleWeekAdminReqDto.getStartDate();
        LocalDate endDate = startDate.plusDays(6);

        List<ScheduleWeekAdminResDto> scheduleWeek = readScheduleWeekAdminReqDto.getCaregiverIds().stream()
                .map(CaregiverIdReqDto::getCaregiverId)
                .flatMap(caregiverId ->
                        scheduleRepository.findAllByMember_MemberIdAndIsDeletedFalse(caregiverId).stream()
                                .filter(schedule ->
                                        !schedule.getEndDate().isBefore(startDate) &&
                                                !schedule.getStartDate().isAfter(endDate))
                                .flatMap(schedule -> {
                                    LocalDate effectiveStart = schedule.getStartDate().isBefore(startDate) ? startDate : schedule.getStartDate();
                                    LocalDate effectiveEnd = schedule.getEndDate().isAfter(endDate) ? endDate : schedule.getEndDate();

                                    return effectiveStart.datesUntil(effectiveEnd.plusDays(1))
                                            .filter(date -> (schedule.getWorkDay() & (1 << (date.getDayOfWeek().getValue() % 7))) != 0)
                                            .map(date -> ScheduleWeekAdminResDto.builder()
                                                    .scheduleId(schedule.getScheduleId())
                                                    .caregiverId(caregiverId)
                                                    .patientId(schedule.getPatientId())
                                                    .scheduleDate(date)
                                                    .startTime(schedule.getStartTime())
                                                    .endTime(schedule.getEndTime())
                                                    .patientName(schedule.getPatient())
                                                    .build());
                                })
                )
                .toList();

        return ScheduleWeekAdminListResDto.from(scheduleWeek);
    }

    @Override
    public ScheduleDayAdminResDto getScheduleDayByAdmin(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 스케줄 입니다"));
        Member member = memberRepository.findByMemberId(schedule.getMember().getMemberId());
        return ScheduleDayAdminResDto.from(schedule, schedule.getMember().getMemberName(), member.getProfileImageUrl());
    }

    @Override
    public ScheduleReadResDto readSchedule(Long scheduleId) {

        return null;
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
                .patientId(updateScheduleRequestDto.getPatientId())
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

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateScheduleStatus() {
        LocalDate today = LocalDate.now();

        List<Schedule> schedules = scheduleRepository.findAll();

        for (Schedule schedule : schedules) {
            LocalDate start = schedule.getStartDate();
            LocalDate end = schedule.getEndDate();

            ScheduleStatus newStatus;
            if (today.isBefore(start)) {
                newStatus = ScheduleStatus.PLANNED;
            } else if (!today.isAfter(end)) {
                newStatus = ScheduleStatus.ONGOING;
            } else {
                newStatus = ScheduleStatus.COMPLETED;
            }

            if (schedule.getStatus() != newStatus) {
                schedule.updateStatus(newStatus);
            }
        }
    }

}