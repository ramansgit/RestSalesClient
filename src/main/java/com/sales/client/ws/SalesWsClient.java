package com.sales.client.ws;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.sales.client.model.Message;
import com.sales.client.model.Message.MessageDecoder;
import com.sales.client.model.Message.MessageEncoder;
import com.sales.client.rest.SalesRestClientImpl;

@ClientEndpoint(encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class SalesWsClient {
	private static final Logger log = Logger.getLogger(SalesWsClient.class.getName());

	@OnOpen
	public void onOpen(final Session session) throws IOException, EncodeException {
		session.getBasicRemote().sendObject(new Message("Client", "Hello!"));

	}

	@OnMessage
	public void onMessage(final Message message) {
		log.info(String.format("Received message '%s' from '%s'", message.getMessage(), message.getClientId()));

		if (message.getMessage() != null && message.getMessage().equalsIgnoreCase("CLIENT_CONNECTED")) {
			new SalesRestClientImpl().uploadSalesExcelFile(message.getClientId());
		}
		if (message.getMessage() != null && message.getMessage().equalsIgnoreCase("CSV_READY")) {
			new SalesRestClientImpl().viewSalesData(message.getClientId());
		}

	}

	@OnClose
	public void onClose(final Session session) {
		System.out.println("client closed ");

	}

}
