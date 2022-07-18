package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        log.info("**************** printing logs: ");
        RequestContext ctx = RequestContext.getCurrentContext(); // 현재 웹 관련된 context 가져오기
        HttpServletRequest request = ctx.getRequest(); // 사용자가 가진 Request 정보 출력
        log.info("**************** " + request.getRequestURI()); // 사용자가 어떤 정보를 요청했는지

        return null;
    }

    @Override
    public String filterType() {
        return "pre"; // 사전 필터
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true; // 필터로 사용하겠다
    }
}
