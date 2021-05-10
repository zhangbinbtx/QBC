package com.quaero.qbcTest.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * 为STOMP消息传递配置Spring
 * WebSocketConfig被注释@Configuration为表明它是一个Spring配置类。
 * @EnableWebSocketMessageBroker: @EnableWebSocketMessageBroker启用WebSocket消息处理，由消息代理支持。
 */
@Configuration //@Configuration注解表明它是一个Spring配置类
@EnableWebSocketMessageBroker //通过@EnableWebSocketMessageBroker 注解启用WebSocket消息处理，由消息代理支持。
public class WebsocketConfig  implements WebSocketMessageBrokerConfigurer{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * 该registerStompEndpoints()方法注册“/serverStatus”端点，
         * 启用SockJS后备选项，以便在WebSocket不可用时可以使用替代传输。
         * SockJS客户端将尝试连接到“/serverStatus”并使用可用的最佳传输（websocket，xhr-streaming，xhr-polling等）。
         * setAllowedOrigins: 允许跨域
         */
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }
 
    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/app"); //为绑定了@MessageMapping注解方法的消息指定“/ app”前缀,该前缀将用于定义所有消息映射
        //点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理,群发（mass），单独聊天（alone）
        registry.enableSimpleBroker("/topic","/user","/mass","/alone");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        registry.setUserDestinationPrefix("/user");
        registry.setApplicationDestinationPrefixes("/app");

    }
 
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
 
    }
 
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
 
    }
 
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
 
    }
 
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
 
    }
 
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
 
    }
 
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return false;
    }
}

