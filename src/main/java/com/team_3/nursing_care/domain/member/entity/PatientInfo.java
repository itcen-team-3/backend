package com.team_3.nursing_care.domain.member.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "patient_info")
@NoArgsConstructor
public class PatientInfo extends BaseEntity {

    @Id
    private Long memberId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String patientLevel;
    @Column(nullable = false)
    private String guardianPhoneNumber;
    @Column(nullable = false)
    private String relationship;

    @Builder
    public PatientInfo(Member member, String patientLevel, String guardianPhoneNumber, String relationship) {
        this.member = member;
        this.patientLevel = patientLevel;
        this.guardianPhoneNumber = guardianPhoneNumber;
        this.relationship = relationship;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
