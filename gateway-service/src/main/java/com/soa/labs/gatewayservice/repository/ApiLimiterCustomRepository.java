package com.soa.labs.gatewayservice.repository;

import com.soa.labs.gatewayservice.model.ApiLimiter;
import reactor.core.publisher.Mono;

public interface ApiLimiterCustomRepository {
    Mono<ApiLimiter> findMatchesApiLimiter(String path, String method);
}
