package com.cts.elearn.filter;

import com.cts.elearn.constants.RequestConstants;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestIdFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String requestId = UUID.randomUUID().toString();

        exchange.getAttributes()
                .put(RequestConstants.REQUEST_ID_ATTRIBUTE, requestId);

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder ->
                        builder.header(RequestConstants.REQUEST_ID, requestId))
                .build();

        mutatedExchange.getResponse()
                .getHeaders()
                .add(RequestConstants.REQUEST_ID, requestId);

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -99;
    }

}