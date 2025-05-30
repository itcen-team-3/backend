package com.team_3.nursing_care.domain.member.proxy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.team_3.nursing_care.domain.member.proxy.dto.BusinessAuthenticityDto;
import com.team_3.nursing_care.domain.member.proxy.dto.BusinessListWrapperDto;
import com.team_3.nursing_care.domain.member.proxy.dto.ReqBusinessAuthenticityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessAuthenticityServiceImpl implements BusinessAuthenticityService {

    private final WebClient webClient;

    @Value("${business_authenticity.url}")
    private String businessAuthenticityUrl;
    @Value("${business_authenticity.path}")
    private String businessAuthenticityPath;
    @Value("${business_authenticity.service_key}")
    private String businessAuthenticityServiceKey;

    @Override
    public Mono<?> validate(ReqBusinessAuthenticityDto request) {

        BusinessAuthenticityDto requestDto = BusinessAuthenticityDto.create(request.getB_no(), request.getStart_dt(), request.getP_nm());
        BusinessListWrapperDto requestWrapperDto = new BusinessListWrapperDto(List.of(requestDto));

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(businessAuthenticityUrl)
                .path(businessAuthenticityPath)
                .queryParam("serviceKey", businessAuthenticityServiceKey)
                .build();

        log.debug("requestWrapperDto: {}", requestWrapperDto);
        log.debug("URI: {}", uriComponents.toUri());

        return webClient.post()
                .uri(uriComponents.toUri())
                .bodyValue(requestWrapperDto)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> {
                    JsonNode dataNode = jsonNode.path("data");

                    for (JsonNode node : dataNode) {
                        JsonNode validNode = node.path("valid");
                        if (validNode != null && "01".equals(validNode.asText())) break;
                        if (validNode != null && "02".equals(validNode.asText()))
                            return Mono.error(new BadRequestException("Business authenticity validation failed"));
                    }
                    return Mono.just(jsonNode);
                })
                .onErrorMap(e -> {
                    if (e instanceof BadRequestException) return e;
                    return new RuntimeException("API 호출 실패 또는 응답 처리 오류", e);
                });

    }
}
