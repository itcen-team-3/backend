package com.team_3.nursing_care.domain.care_log.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCreateCareLogDto {

    @NotBlank
    private Long careGiverId;
    @NotBlank
    private Long patientId;
    @NotNull
    private MultipartFile signFile;

    private String description;

    private List<MultipartFile> imageFileList;
    private List<CareItemDto> careItemList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CareItemDto {
        private Integer careItemId;
        private Integer requiredMinutes;
    }

}
