package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatientsNameListResponseDto {

    private List<PatientsNameResponseDto> patients;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private boolean isLast;

    @Builder
    public PatientsNameListResponseDto(List<PatientsNameResponseDto> patients,
                                         int totalPages, long totalElements, int currentPage, boolean isLast) {
        this.patients=patients;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.isLast = isLast;
    }

    public static PatientsNameListResponseDto from(Page<PatientsNameResponseDto> page) {
        return PatientsNameListResponseDto.builder()
                .patients(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .isLast(page.isLast())
                .build();
    }
}
