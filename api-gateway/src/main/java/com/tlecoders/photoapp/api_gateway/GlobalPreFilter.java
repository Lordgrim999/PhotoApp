package com.tlecoders.photoapp.api_gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GlobalPreFilter implements GlobalFilter {
    private final Logger log= LoggerFactory.getLogger(GlobalPreFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath=exchange.getRequest().getPath().toString();
        log.info("Request Path={}",requestPath);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        headers.keySet().forEach((headerName)->{
            log.info("{} {}", headerName, headers.getFirst(headerName));
        });
        return chain.filter(exchange);
    }
}
