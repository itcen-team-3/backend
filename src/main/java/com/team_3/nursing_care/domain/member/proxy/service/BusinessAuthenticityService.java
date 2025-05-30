package com.team_3.nursing_care.domain.member.proxy.service;

import com.team_3.nursing_care.domain.member.proxy.dto.ReqBusinessAuthenticityDto;
import reactor.core.publisher.Mono;

public interface BusinessAuthenticityService {
    Mono<?> validate(ReqBusinessAuthenticityDto request);
}
