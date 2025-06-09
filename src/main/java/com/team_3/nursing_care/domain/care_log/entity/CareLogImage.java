package com.team_3.nursing_care.domain.care_log.entity;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_log_id")
    private CareLog careLog;

    public static CareLogImage create(String imageUrl) {
        return CareLogImage.builder()
                .imageUrl(imageUrl)
                .build();
    }

    public void connectCareLog(CareLog careLog) {
        this.careLog = careLog;
    }
}
