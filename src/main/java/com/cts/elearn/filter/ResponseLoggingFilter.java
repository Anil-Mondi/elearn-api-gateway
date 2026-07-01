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
public class ResponseLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ResponseLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    Long startTime = exchange.getAttribute("START_TIME");

                    long executionTime =
                            startTime == null
                                    ? 0
                                    : System.currentTimeMillis() - startTime;

                    LOGGER.info("""
                            Outgoing Response
                            Status          : {}
                            Correlation Id  : {}
                            Request Id      : {}
                            Time            : {} ms
                            """,
                            exchange.getResponse().getStatusCode(),
                            exchange.getAttribute(RequestConstants.CORRELATION_ID_ATTRIBUTE),
                            exchange.getAttribute(RequestConstants.REQUEST_ID_ATTRIBUTE),
                            executionTime);

                }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}