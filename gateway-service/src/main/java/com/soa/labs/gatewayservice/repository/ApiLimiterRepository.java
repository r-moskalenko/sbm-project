package com.soa.labs.gatewayservice.repository;

import com.soa.labs.gatewayservice.model.ApiLimiter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ApiLimiterRepository extends ReactiveCrudRepository<ApiLimiter, Long>, ApiLimiterCustomRepository {
}
