package com.team_3.nursing_care.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.nursing_care.domain.member.entity.Company;
import com.team_3.nursing_care.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
public class MemberInfoRequestDto {

    private Long companyId;

    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    @NotNull(message = "생년월일은 필수값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    @NotBlank(message = "주소는 필수값입니다.")
    private String address;

    private String description;
    private MultipartFile profileImage;

    @Builder
    public MemberInfoRequestDto(Long companyId,
                                String name,
                                LocalDate birthDate,
                                String phoneNumber,
                                String address,
                                String description,
                                MultipartFile profileImage){
        this.companyId = companyId;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.profileImage = profileImage;
    }

    public Member toEntity(String profileImageUrl,Company company){

        return Member.builder()
                .company(company)
                .memberName(this.name)
                .birthDate(this.birthDate)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .description(this.description)
                .profileImage(profileImageUrl)
                .build();
    }
}
