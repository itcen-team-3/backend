package com.team_3.nursing_care.domain.care_log.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqUpdateCareLogDto {

    private MultipartFile signFile;
    private String description;
    private List<MultipartFile> imageFileList;
    private List<ReqCreateCareLogDto.CareItemDto> careItemList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CareItemDto {
        private Integer careItemId;
        private Integer requiredMinutes;
    }
}
