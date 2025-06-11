package com.team_3.nursing_care.domain.salary.scheduler;

import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import com.team_3.nursing_care.domain.attendance.repository.AttendanceLogRepository;
import com.team_3.nursing_care.domain.salary.entity.Salary;
import com.team_3.nursing_care.domain.salary.repository.SalaryRepository;
import com.team_3.nursing_care.domain.schedule.constant.ScheduleStatus;
import com.team_3.nursing_care.domain.schedule.entity.Schedule;
import com.team_3.nursing_care.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryScheduler {

    private final SalaryRepository salaryRepository;
    private final ScheduleRepository scheduleRepository;
    private final AttendanceLogRepository attendanceLogRepository;

    @Scheduled(cron = "0 13 14 L * ?")

//    @Scheduled(cron = "0 13 14 * * ?")
    @Transactional
    public void schedule() {
        List<Schedule> scheduleList = scheduleRepository.findAllForScheduler(ScheduleStatus.ONGOING);


        List<Salary> saveList = new ArrayList<>();
        List<Salary> existedSalaryList = salaryRepository.findByScheduleIn(scheduleList);

        scheduleList.forEach(schedule -> {
            LocalDate startDate = schedule.getStartDate();
            LocalDate endDate = schedule.getEndDate();

            Salary existedSalary = existedSalaryList.stream()
                    .filter(salary -> salary.getSchedule().equals(schedule))
                    .max(Comparator.comparing(Salary::getStartDate)).orElse(null);

            if (existedSalary == null) {
                List<AttendanceLog> attendanceLogList = attendanceLogRepository.findLogForSalary(schedule.getMember(), schedule.getPatientId(), startDate.atStartOfDay(), startDate.atStartOfDay().plusDays(30));
                saveList.add(calculateCreateSalary(schedule, attendanceLogList, schedule.getStartDate()));

            } else {
                LocalDate salaryEndDate = existedSalary.getEndDate();

                if (salaryEndDate.plusDays(30).isBefore(schedule.getEndDate())) {
                    List<AttendanceLog> attendanceLogList = attendanceLogRepository.findLogForSalary(schedule.getMember(), schedule.getPatientId(), salaryEndDate.atStartOfDay().plusDays(1), salaryEndDate.atStartOfDay().plusDays(30));
                    saveList.add(calculateCreateSalary(schedule, attendanceLogList, endDate));
                } else {
                    List<AttendanceLog> attendanceLogList = attendanceLogRepository.findLogForSalary(schedule.getMember(), schedule.getPatientId(), salaryEndDate.atStartOfDay().plusDays(1), schedule.getEndDate().atStartOfDay().plusDays(1));
                    saveList.add(calculateCreateSalary(schedule, attendanceLogList, salaryEndDate.plusDays(1)));
                }
            }

        });

        salaryRepository.saveAll(saveList);
    }

    private Salary calculateCreateSalary(Schedule schedule, List<AttendanceLog> attendanceLogList, LocalDate startDate) {
        List<AttendanceLog> list = attendanceLogList.stream().filter(attendanceLog -> !attendanceLog.getCheckOutStatus().equals(CheckOutStatus.ON_TIME) || !attendanceLog.getCheckInStatus().equals(CheckInStatus.ON_TIME)).toList();

        int totalCost = 0;
        double workHours = 0;

        if (list.isEmpty()) {
            for (AttendanceLog al : attendanceLogList) {
                int paymentForHour = schedule.getPaymentForHour();
                long durationMillis = schedule.getEndTime().getTime() - schedule.getStartTime().getTime();
                double totalHoursDouble = (double) durationMillis / (1000.0 * 60 * 60);
                totalCost += (int) (totalHoursDouble * paymentForHour);
                workHours += totalHoursDouble;
            }
        } else {
            for (AttendanceLog al : list) {
                int paymentForHour = schedule.getPaymentForHour();
                Duration duration = Duration.between(al.getCheckIn(), al.getCheckOut());
                long durationMillis = duration.toMillis();
                double totalHoursDouble = (double) durationMillis / (1000.0 * 60 * 60);
                double calculatedCostForLog = totalHoursDouble * paymentForHour;
                totalCost += (int) calculatedCostForLog;
                workHours += totalHoursDouble;
            }
        }

        return Salary.create(schedule, startDate, totalCost, workHours);
    }
}
