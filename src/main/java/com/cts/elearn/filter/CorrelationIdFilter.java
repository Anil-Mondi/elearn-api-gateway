package com.cts.elearn.filter;

import java.util.UUID;

import com.cts.elearn.constants.RequestConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String incomingCorrelationId = exchange.getRequest()
                .getHeaders()
                .getFirst(RequestConstants.CORRELATION_ID);

        final String correlationId =
                (incomingCorrelationId == null || incomingCorrelationId.isBlank())
                        ? UUID.randomUUID().toString()
                        : incomingCorrelationId;

        exchange.getAttributes().put(
                RequestConstants.CORRELATION_ID_ATTRIBUTE,
                correlationId
        );

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(request -> request.header(
                        RequestConstants.CORRELATION_ID,
                        correlationId))
                .build();

        mutatedExchange.getResponse()
                .getHeaders()
                .set(RequestConstants.CORRELATION_ID, correlationId);

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

}