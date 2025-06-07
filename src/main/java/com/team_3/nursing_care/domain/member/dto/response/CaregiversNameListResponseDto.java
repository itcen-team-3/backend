package com.team_3.nursing_care.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class CaregiversNameListResponseDto {

    private List<CaregiversNameResponseDto> caregivers;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private boolean isLast;

    @Builder
    public CaregiversNameListResponseDto(List<CaregiversNameResponseDto> caregivers,
                                         int totalPages, long totalElements, int currentPage, boolean isLast) {
        this.caregivers = caregivers;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.isLast = isLast;
    }

    public static CaregiversNameListResponseDto from(Page<CaregiversNameResponseDto> page) {
        return CaregiversNameListResponseDto.builder()
                .caregivers(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .isLast(page.isLast())
                .build();
    }
}