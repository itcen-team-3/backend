package com.team_3.nursing_care.domain.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "company")
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    private String companyName;

    private String registrationNumber;

    private String ceoName;

    private String bizRegUrl;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();

    @Builder
    public Company(Long companyId, String companyName, String registrationNumber, String ceoName, String bizRegUrl) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.ceoName = ceoName;
        this.bizRegUrl = bizRegUrl;
    }


}
