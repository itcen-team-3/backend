package com.team_3.nursing_care.domain.schedule.service;


import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import com.team_3.nursing_care.domain.schedule.dto.request.CreateScheduleRequestDto;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void addWorkSchedule(CreateScheduleRequestDto createScheduleRequestDto) {
        Member caregiver = memberRepository.findById(createScheduleRequestDto.getCaregiverId())
                .orElseThrow(() -> new IllegalArgumentException("환자(Member)를 찾을 수 없습니다."));
        scheduleRepository.save(createScheduleRequestDto.toEntity(caregiver));
    }

    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalStateException("존재 하지 않는 스케줄 ID 입니다."));
        schedule.updateIsDelete(true);
    }
}
