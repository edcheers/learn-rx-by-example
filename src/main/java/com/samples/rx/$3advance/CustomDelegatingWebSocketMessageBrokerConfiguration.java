package com.samples.rx.$3advance;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;
import org.springframework.web.socket.messaging.WebSocketAnnotationMethodMessageHandler;

import java.util.List;

@Configuration
public class CustomDelegatingWebSocketMessageBrokerConfiguration
        extends DelegatingWebSocketMessageBrokerConfiguration {


    @Override
    protected SimpAnnotationMethodMessageHandler createAnnotationMethodMessageHandler() {

        return new WebSocketAnnotationMethodMessageHandler(clientInboundChannel(),
                clientOutboundChannel(), brokerMessagingTemplate()) {

            /**
             * Add the return value handler to deal with Observables
             */
            @Override
            protected List<HandlerMethodReturnValueHandler> initReturnValueHandlers() {
                return ImmutableList.<HandlerMethodReturnValueHandler>builder()
                        .add(getApplicationContext()
                                .getBean(ObservableSubscriptionMethodReturnValueHandler.class))
                        .addAll(super.initReturnValueHandlers()).build();
            }

        };
    }
}
