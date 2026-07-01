package com.cts.elearn.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cts.elearn.constants.RequestConstants;

import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String correlationId = exchange.getAttribute(
                RequestConstants.CORRELATION_ID_ATTRIBUTE);

        String requestId = exchange.getAttribute(
                RequestConstants.REQUEST_ID_ATTRIBUTE);

        LOGGER.info("""
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

        LOGGER.info("Method: {}", exchange.getRequest().getMethod());
        LOGGER.info("User-Agent: {}", exchange.getRequest().getHeaders().getFirst("User-Agent"));
        LOGGER.info("Host: {}", exchange.getRequest().getHeaders().getFirst("Host"));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -98;
    }

}