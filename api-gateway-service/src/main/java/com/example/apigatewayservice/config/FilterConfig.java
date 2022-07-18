package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {
    // application.yml 파일에 routing 정보 등록했던 것을 자바코드로 처리하게 되는 것

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                       .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://localhost:8081")) // r이라는 값이 전달되면 path를 확인하고 filter적용해서 uri 로 이동시켜준다
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082")) // r이라는 값이 전달되면 path를 확인하고 filter적용해서 uri 로 이동시켜준다
                .build();
    }
}
