package com.team_3.nursing_care.domain.care_log.dto.request;

import com.team_3.nursing_care.domain.care_log.constant.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private MultipartFile imageFile;
    private ImageType imageType;
}
