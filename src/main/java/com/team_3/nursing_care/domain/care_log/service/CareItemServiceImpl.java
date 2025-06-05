package com.team_3.nursing_care.domain.care_log.service;

import com.team_3.nursing_care.common.exception.CareLogException;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareItemDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareItemDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareItemDto;
import com.team_3.nursing_care.domain.care_log.entity.CareItem;
import com.team_3.nursing_care.domain.care_log.repository.CareItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareItemServiceImpl implements CareItemService {

    private final CareItemRepository careItemRepository;

    @Override
    @Transactional
    public void createCareItem(ReqCreateCareItemDto reqCreateCareItemDto) {
        careItemRepository.save(CareItem.create(reqCreateCareItemDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResCareItemDto> getCareItemListIsDeletedFalse() {
        return careItemRepository.findAllByIsDeletedFalse().stream().map(ResCareItemDto::create).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResCareItemDto> getCareItemList() {
        return careItemRepository.findAll().stream().map(ResCareItemDto::create).toList();
    }

    @Override
    @Transactional
    public ResCareItemDto updateCareItemById(Integer id, ReqUpdateCareItemDto reqUpdateCareItemDto) {
        CareItem careItem = careItemRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new CareLogException(HttpStatusCode.NOT_FOUND, "not found Care item ( is deleted false ) by id: " + id));
        CareItem updateEntity = careItem.update(reqUpdateCareItemDto);
        careItemRepository.save(updateEntity);
        return ResCareItemDto.create(updateEntity);
    }

    @Override
    @Transactional
    public void deleteCareItemById(Integer id, CustomUserDetails userDetails) {
        CareItem careItem = careItemRepository.findById(id).orElseThrow(() -> new CareLogException(HttpStatusCode.NOT_FOUND, "not found care item by id: " + id));
        careItem.softDelete(userDetails.getMemberId());
    }
}
