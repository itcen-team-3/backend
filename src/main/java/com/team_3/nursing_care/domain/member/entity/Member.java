package com.team_3.nursing_care.domain.member.entity;

import com.team_3.nursing_care.domain.member.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String memberName;

    private String phoneNumber;

    private LocalDate birthDate;

    private String address;

    private String profileImage;

    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String id;

    private String password;


}
