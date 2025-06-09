package com.team_3.nursing_care.domain.care_log.service;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqCreateCareItemDto;
import com.team_3.nursing_care.domain.care_log.dto.request.ReqUpdateCareItemDto;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareItemDto;

import java.util.List;

public interface CareItemService {

    void createCareItem(ReqCreateCareItemDto reqCreateCareItemDto);

    List<ResCareItemDto> getCareItemListIsDeletedFalse();

    List<ResCareItemDto> getCareItemList();

    ResCareItemDto updateCareItemById(Integer id, ReqUpdateCareItemDto reqUpdateCareItemDto);

    void deleteCareItemById(Integer id, CustomUserDetails userDetails);
}
