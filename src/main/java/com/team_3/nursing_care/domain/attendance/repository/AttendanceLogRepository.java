package com.team_3.nursing_care.domain.attendance.repository;

import com.team_3.nursing_care.domain.attendance.constant.CheckInStatus;
import com.team_3.nursing_care.domain.attendance.constant.CheckOutStatus;
import com.team_3.nursing_care.domain.attendance.entity.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {

    List<AttendanceLog> findByMember_MemberIdAndCheckInStatusOrCheckOutStatus(Long memberId, CheckInStatus checkInStatus, CheckOutStatus checkOutStatus);

}
