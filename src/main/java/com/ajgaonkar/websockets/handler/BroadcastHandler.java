package com.ajgaonkar.websockets.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BroadcastHandler extends TextWebSocketHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);

	private final Set<WebSocketSession> sessions = new HashSet<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("BroadcastHandler - afterConnectionEstablished, session = {}", session);
		TextMessage msg = new TextMessage("BroadcastHandler - ack - afterConnectionEstablished");
		session.sendMessage(msg);
		sessions.add(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		LOGGER.info("BroadcastHandler - handleTextMessage, session = {}, message = {}", session, message);
		TextMessage msg = new TextMessage("BroadcastHandler - ack - handleTextMessage");
		session.sendMessage(msg);
		broadCast(message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.info("BroadcastHandler - afterConnectionClosed, session = {}", session);
		sessions.remove(session);
	}

	private void broadCast(TextMessage message){
		final String time = new SimpleDateFormat("HH:mm").format(new Date());
		TextMessage outputMessage = new TextMessage(time + " " + message.getPayload());

		sessions.forEach( c -> {
			if(!c.isOpen()){
				return;
			}
			try {
				c.sendMessage(outputMessage);
			} catch (IOException e) {
				LOGGER.error("session = {}, message = {}", c, outputMessage, e);
			}
		});
	}
}
