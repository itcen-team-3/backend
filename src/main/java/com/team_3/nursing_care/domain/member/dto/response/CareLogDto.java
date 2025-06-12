package com.team_3.nursing_care.domain.member.dto.response;

import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareLogDto {

    private Long careLogId;
    private String careGiverName;
    private LocalDateTime createDate;
    private Integer activeCount;

    public static CareLogDto create(CareLog careLog) {
        return CareLogDto.builder()
                .careLogId(careLog.getId())
                .careGiverName(careLog.getCareGiver().getMemberName())
                .createDate(careLog.getCreateDate())
                .activeCount(careLog.getCareDetailList().size())
                .build();
    }
}
