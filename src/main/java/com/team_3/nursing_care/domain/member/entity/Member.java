package com.team_3.nursing_care.domain.member.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.request.ReqSignUpDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdateCaregiverRequestDto;
import com.team_3.nursing_care.domain.member.dto.request.UpdatePatientRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String address;
    @Column(nullable = true)
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 요양보호사 필드
    private String description;
    private String certificateNumber;
    private short career;

    @Setter
    private LocalDateTime lastLoginAt;

    private String loginId;
    private String loginPw;

    @Builder.Default
    private Boolean accountIsDeleted = false;

    @Setter
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member admin;

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Member> caregivers = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private PatientInfo patientInfo;

    public static Member createAdmin(ReqSignUpDto reqSignUpDto, String encodedPw) {
        return Member.builder()
                .memberName(reqSignUpDto.getRepresentativeName())
                .phoneNumber(reqSignUpDto.getPhoneNumber())
                .birthDate(reqSignUpDto.getBirthDate())
                .address(reqSignUpDto.getCompanyAddress())
                .role(Role.ADMIN)
                .loginId(reqSignUpDto.getLoginId())
                .loginPw(encodedPw)
                .build();
    }

    public void connectCompany(Company company) {
        this.company = company;
        company.getMemberList().add(this);
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
        patientInfo.setMember(this);
    }

    public void updateCaregiver(UpdateCaregiverRequestDto dto, String profileImageUrl) {
        this.memberName = dto.getName();
        this.birthDate = dto.getBirthDate();
        this.address = dto.getAddress();
        this.phoneNumber = dto.getPhoneNumber();
        this.certificateNumber = dto.getCertificateNumber();
        this.career = dto.getCareer();
        this.description = dto.getDescription();
        this.profileImageUrl = profileImageUrl;
    }

    public void updatePatient(UpdatePatientRequestDto dto, String profileImageUrl) {
        this.memberName = dto.getName();
        this.birthDate = dto.getBirthDate();
        this.address = dto.getAddress();
        this.phoneNumber = dto.getPhoneNumber();
        this.description = dto.getDescription();
        this.profileImageUrl = profileImageUrl;
    }

    public void updateAccount(String loginId, String loginPw) {
        this.loginId = loginId;
        this.loginPw = loginPw;
    }

    public void updateIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
