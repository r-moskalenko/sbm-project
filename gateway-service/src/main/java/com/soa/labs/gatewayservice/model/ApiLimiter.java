package com.soa.labs.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("api_limiter")
public class ApiLimiter implements Serializable {

    private static final long serialVersionUID = -5132504076641395736L;

    @Id
    private Long id;

    private String path;
    private String method;

    private int threshold;
    private int ttl;

    private boolean active;
}
