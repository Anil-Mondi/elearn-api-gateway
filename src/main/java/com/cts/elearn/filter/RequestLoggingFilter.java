package com.cts.elearn.filter;

import com.cts.elearn.constants.RequestConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String correlationId = exchange.getAttribute(
                RequestConstants.CORRELATION_ID_ATTRIBUTE);

        String requestId = exchange.getAttribute(
                RequestConstants.REQUEST_ID_ATTRIBUTE);

        log.info("""
                        Incoming Request
                        Method          : {}
                        URI             : {}
                        Correlation Id  : {}
                        Request Id      : {}
                        """,
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI(),
                correlationId,
                requestId);

        exchange.getAttributes()
                .put("START_TIME", System.currentTimeMillis());

        log.info("Method: {}", exchange.getRequest().getMethod());
        log.info("User-Agent: {}", exchange.getRequest().getHeaders().getFirst("User-Agent"));
        log.info("Host: {}", exchange.getRequest().getHeaders().getFirst("Host"));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -98;
    }

}