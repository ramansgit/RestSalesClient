package com.rest.sales.client;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.rest.sales.client.Message.MessageDecoder;
import com.rest.sales.client.Message.MessageEncoder;

@ClientEndpoint(encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class BroadcastClientEndpoint {
	private static final Logger log = Logger.getLogger(BroadcastClientEndpoint.class.getName());
	
	@OnOpen
	public void onOpen(final Session session) throws IOException, EncodeException {
		 session.getBasicRemote().sendObject( new Message( "Client", "Hello!"
		) );
		
	}

	
	
	@OnMessage
	public void onMessage(final Message message) {
		log.info(String.format("Received message '%s' from '%s'", message.getMessage(), message.getClientId()));

		if (message.getMessage() != null && message.getMessage().equalsIgnoreCase("CLIENT_CONNECTED")) {
			new SalesClientImpl().uploadSalesExcelFile(message.getClientId());
		}
		if (message.getMessage() != null && message.getMessage().equalsIgnoreCase("CSV_READY")) {
			new SalesClientImpl().viewSalesData(message.getClientId());
		}

	}
	
	@OnClose
	public void onClose(final Session session) {
		System.out.println("client closed ");
		
	}
	
	

}
