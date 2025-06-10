package com.team_3.nursing_care.domain.care_log.dto.response;

import com.team_3.nursing_care.domain.care_log.constant.CareItemType;
import com.team_3.nursing_care.domain.care_log.entity.CareDetail;
import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import com.team_3.nursing_care.domain.care_log.entity.CareLogImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCareLogDetailDto {

    private String careGiverName;
    private String patientName;
    private Time startTime;
    private Time endTime;
    private LocalDate createDate;

    private List<CareDetailDto> careDetailList;

    private List<String> imageUrlList;
    private String signUrl;
    private String description;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    protected static class CareDetailDto {
        private CareItemType careItemType;
        private String careItemName;
        private Integer requiredMinutes;

        protected static CareDetailDto create(CareDetail careDetail) {
            return CareDetailDto.builder()
                    .careItemType(careDetail.getCareItem().getType())
                    .careItemName(careDetail.getCareItem().getName())
                    .requiredMinutes(careDetail.getRequiredMinutes())
                    .build();
        }
    }

    public static ResCareLogDetailDto create(CareLog careLog, Time startTime, Time endTime) {
        return ResCareLogDetailDto.builder()
                .careGiverName(careLog.getCareGiver().getMemberName())
                .patientName(careLog.getPatientName())
                .startTime(startTime)
                .endTime(endTime)
                .careDetailList(careLog.getCareDetailList().stream().map(CareDetailDto::create).collect(Collectors.toList()))
                .imageUrlList(careLog.getCareLogImageList().stream().map(CareLogImage::getImageUrl).collect(Collectors.toList()))
                .createDate(LocalDate.from(careLog.getCreateDate()))
                .signUrl(careLog.getSignUrl())
                .description(careLog.getDescription())
                .build();
    }

}
