package com.team_3.nursing_care.domain.member.entity;

import com.team_3.nursing_care.common.auditor.BaseEntity;
import com.team_3.nursing_care.domain.member.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String profileImage;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String certificateNumber;
    private short career;

    private LocalDateTime lastLoginAt;

    private String id;

    private String password;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member admin;

    @OneToMany(mappedBy = "admin", cascade =CascadeType.ALL)
    private List<Member> caregivers = new ArrayList<>();

}
