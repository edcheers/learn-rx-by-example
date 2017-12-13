package com.samples.rx.$3advance;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.springframework.context.event.EventListener;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SubscriptionMethodReturnValueHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static org.springframework.messaging.handler.DestinationPatternsMessageCondition.LOOKUP_DESTINATION_HEADER;

/**
 * {@code HandlerMethodReturnValueHandler} for replying directly to a
 * subscription. It is supported on methods annotated with
 * {@link org.springframework.messaging.simp.annotation.SubscribeMapping
 * SubscribeMapping} such that the return value is treated as a response to be
 * sent directly back on the session. This allows a client to implement
 * a request-response pattern and use it for example to obtain some data upon
 * initialization.
 * <p>
 * <p>The value returned from the method is converted and turned into a
 * {@link Message} that is then enriched with the sessionId, subscriptionId, and
 * destination of the input message.
 */
@Component
public class ObservableSubscriptionMethodReturnValueHandler extends SubscriptionMethodReturnValueHandler {

    private final Multimap<String, Disposable> subscriptions = ArrayListMultimap.create();
    private final SimpMessagingTemplate template;

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        final String sessionId = event.getSessionId();
        subscriptions.get(sessionId).forEach(Disposable::dispose);
        subscriptions.removeAll(sessionId);
    }

    /**
     * Construct a new SubscriptionMethodReturnValueHandler.
     *
     * @param template a messaging template to send messages to,
     *                 most likely the "clientOutboundChannel" (must not be {@code null})
     */
    public ObservableSubscriptionMethodReturnValueHandler(SimpMessagingTemplate template) {
        super(template);
        this.template = template;
    }


    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Observable.class.isAssignableFrom(returnType.getParameterType())
                && super.supportsReturnType(returnType);
    }


    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, Message<?> message) throws Exception {

        if (returnValue instanceof Observable) {

            final String destination = (String) message.getHeaders().get(LOOKUP_DESTINATION_HEADER);
            final String sessionId = SimpMessageHeaderAccessor.getSessionId(message.getHeaders());

            final Observable<?> observable = (Observable) returnValue;

            final Disposable subscription = observable.subscribe(
                    returnedObject ->
                            template.convertAndSend(destination, returnedObject, createHeaders(sessionId, returnType)),
                    throwable ->
                            template.send(destination, MessageBuilder.createMessage(new byte[0], createErrorHeaders(sessionId, throwable)))
            );

            subscriptions.put(sessionId, subscription);
        }
    }


    /**
     * This method constructs stomp header to be consumed by RxJs
     *
     * @param sessionId       session id
     * @param methodParameter method parameters to be returned as part of the header, instance of {@link MethodParameter}
     * @return instance of {@link MessageHeaders} message to be returned to the client
     */
    private MessageHeaders createHeaders(String sessionId, MethodParameter methodParameter) {
        SimpMessageHeaderAccessor headerAccessor =
                SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        if (getHeaderInitializer() != null) {
            getHeaderInitializer().initHeaders(headerAccessor);
        }
        if (sessionId != null) {
            headerAccessor.setSessionId(sessionId);
        }
        headerAccessor.setHeader(SimpMessagingTemplate.CONVERSION_HINT_HEADER, methodParameter);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }


    /**
     * This method constructs stomp error header to be consumed by RxJs
     *
     * @param sessionId session id
     * @param error     instance of {@link Throwable} error to be passed to the client
     * @return instance of {@link MessageHeaders} message to be returned to the client
     */
    private MessageHeaders createErrorHeaders(String sessionId, Throwable error) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
        headerAccessor.setMessage(error.getMessage());
        headerAccessor.setSessionId(sessionId);

        if (getHeaderInitializer() != null) {
            getHeaderInitializer().initHeaders(headerAccessor);
        }
        if (sessionId != null) {
            headerAccessor.setSessionId(sessionId);
        }
        return headerAccessor.getMessageHeaders();
    }


}
