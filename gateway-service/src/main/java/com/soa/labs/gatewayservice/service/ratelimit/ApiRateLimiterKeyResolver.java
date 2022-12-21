package com.soa.labs.gatewayservice.service.ratelimit;

import com.soa.labs.gatewayservice.helper.ObjectHelper;
import com.soa.labs.gatewayservice.service.ApiLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class ApiRateLimiterKeyResolver implements KeyResolver {

    @Autowired
    private ApiLimiterService apiLimiterService;

    @Autowired
    private ObjectHelper objectHelper;

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return apiLimiterService.findMatchesApiLimiter(
                        exchange.getRequest()
                                .getPath()
                                .value(),
                        exchange.getRequest()
                                .getMethodValue())
                .doOnNext(apiLimiter -> apiLimiter.setPath(
                        exchange.getRequest()
                                .getPath()
                                .value()))
                .map(objectHelper::toStringBase64);
    }
}
