package com.team_3.nursing_care.domain.care_log.dto.response;

import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCareLogDto {

    private Long careLogId;
    private LocalDate createDate;
    private Integer activeCount;

    public static ResCareLogDto create(CareLog careLog) {
        return ResCareLogDto.builder()
                .careLogId(careLog.getId())
                .createDate(LocalDate.from(careLog.getCreateDate()))
                .activeCount(careLog.getCareDetailList().size())
                .build();
    }
}
