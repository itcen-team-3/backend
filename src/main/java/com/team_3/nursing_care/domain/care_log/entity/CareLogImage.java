package com.team_3.nursing_care.domain.care_log.entity;

import com.team_3.nursing_care.domain.care_log.constant.ImageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "care_log_image")
@Builder
public class CareLogImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private ImageType imageType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_log_id")
    private CareLog careLog;

    public static CareLogImage create(String imageUrl, ImageType imageType) {
        return CareLogImage.builder()
                .imageUrl(imageUrl)
                .imageType(imageType)
                .build();
    }

    public void connectCareLog(CareLog careLog) {
        this.careLog = careLog;
    }
}
