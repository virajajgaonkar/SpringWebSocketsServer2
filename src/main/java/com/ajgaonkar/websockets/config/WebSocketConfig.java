package com.ajgaonkar.websockets.config;

import com.ajgaonkar.websockets.handler.BroadcastHandler;
import com.ajgaonkar.websockets.handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MyHandler(), "/myHandler")
				.addHandler(new BroadcastHandler(), "/broadcast")
				.setAllowedOrigins("*")
				.addInterceptors(new HttpSessionHandshakeInterceptor());
	}

	@Bean
	public WebSocketHandler myHandler() {
		return new MyHandler();
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}
}
