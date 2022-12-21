package com.soa.labs.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiLimiterRequest {

    @NotBlank
    private String path;

    @NotBlank
    private String method;

    private int threshold;
    private int ttl;

    private boolean active;
}

