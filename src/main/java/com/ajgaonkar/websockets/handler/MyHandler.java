package com.ajgaonkar.websockets.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MyHandler extends TextWebSocketHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("afterConnectionEstablished, session = {}", session);
		TextMessage msg = new TextMessage("ack - afterConnectionEstablished");
		session.sendMessage(msg);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		LOGGER.info("handleTextMessage, session = {}, message = {}", session, message);
		TextMessage msg = new TextMessage("ack - handleTextMessage");
		session.sendMessage(msg);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.info("afterConnectionClosed, session = {}", session);
	}
}
