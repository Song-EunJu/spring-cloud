package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter(){
        super(Config.class);
    }
    // apply : 어떠한 작동을 할 것인지 적어주는 곳
    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Global Filter baseMessage: {}", config.getBaseMessage());

            if(config.isPreLogger()){ //
                log.info("Global Filter Start:  request id -> {}", request.getId());
            }
            // Custom Post Filter
            // Mono -> webflux , spring5 에서 추가
            // 동기방식서버가 아니라 비동기방식 서버지원할 때 단일값 전달할 때 mono
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.info("Global Filter End: response id -> {}", response.getStatusCode());
                }
            }));
        };
    }
    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
