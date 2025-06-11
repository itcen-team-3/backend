package com.team_3.nursing_care.domain.care_log.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "care_log")
@Builder
public class CareLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String signUrl;
    private String description;

    private Long patientId;
    private String patientName;

    @OneToMany(mappedBy = "careLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<CareDetail> careDetailList = new HashSet<>();
    @OneToMany(mappedBy = "careLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<CareLogImage> careLogImageList = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_giver_id")
    private Member careGiver;

    public static CareLog create(
            Member member,
            Long patientId,
            String patientName,
            String signFileUrl,
            String description
    ) {
        return CareLog.builder()
                .signUrl(signFileUrl)
                .careGiver(member)
                .description(description)
                .patientId(patientId)
                .patientName(patientName)
                .build();
    }

    public void connectCareDetail(CareDetail careDetail) {
        this.careDetailList.add(careDetail);
        careDetail.connectCareLog(this);
    }

    public void connectCareLogImage(CareLogImage careLogImage) {
        this.careLogImageList.add(careLogImage);
        careLogImage.connectCareLog(this);
    }

    public CareLog update(
            String signFileUrl,
            String description,
            List<CareLogImage> newCareLogImageList,
            List<CareDetail> newCareDetailList
    ) {
        this.signUrl = signFileUrl != null ? signFileUrl : this.signUrl;
        this.description = description != null ? description : this.description;

        this.careLogImageList.clear();
        this.careDetailList.clear();

        if (newCareLogImageList != null) newCareLogImageList.forEach(this::connectCareLogImage);
        if (newCareDetailList != null) newCareDetailList.forEach(this::connectCareDetail);

        return this;
    }
}
