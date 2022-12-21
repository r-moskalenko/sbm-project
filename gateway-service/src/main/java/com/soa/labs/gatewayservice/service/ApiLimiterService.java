package com.soa.labs.gatewayservice.service;

import com.soa.labs.gatewayservice.dto.ApiLimiterRequest;
import com.soa.labs.gatewayservice.model.ApiLimiter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiLimiterService {

    Flux<ApiLimiter> findApiLimiters();

    Mono<ApiLimiter> findApiLimiter(Long id);

    Mono<Void> createApiLimiter(ApiLimiterRequest createOrUpdateApiLimiter);

    Mono<Void> updateApiLimiter(Long id, ApiLimiterRequest createOrUpdateApiLimiter);

    Mono<Void> deleteApiLimiter(Long id);

    Mono<Void> updateActivationStatus(Long id, boolean activate);

    Mono<ApiLimiter> findMatchesApiLimiter(String path, String method);
}
