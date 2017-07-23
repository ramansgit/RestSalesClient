package com.sales.client.ws;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.eclipse.jetty.util.component.LifeCycle;

import com.sales.client.constants.SalesClientConstants;
import com.sales.client.model.Message;
import com.sales.client.model.Message.MessageDecoder;
import com.sales.client.model.Message.MessageEncoder;
import com.sales.client.rest.SalesRestClientImpl;

@ClientEndpoint(encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class SalesWsClient {
	private static final Logger log = Logger.getLogger(SalesWsClient.class.getName());

	private static CountDownLatch latch;

	public static void main(final String[] args) throws Exception {

		latch = new CountDownLatch(1);

		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

		try (Session session = container.connectToServer(SalesWsClient.class,
				URI.create(SalesClientConstants.SALES_WS_URI))) {
			latch.await();
		}

		if (container instanceof LifeCycle) {
			((LifeCycle) container).stop();
		}

	}

	@OnOpen
	public void onOpen(final Session session) throws IOException, EncodeException {
		session.getBasicRemote().sendObject(new Message("Client", "Hello!"));

	}

	@OnMessage
	public void onMessage(final Message message) {
		log.info(String.format("Received message '%s' from '%s'", message.getMessage(), message.getClientId()));

		if (message.getMessage() != null
				&& message.getMessage().equalsIgnoreCase(SalesClientConstants.CLIENT_CONNECTED)) {
			new SalesRestClientImpl().uploadSalesExcelFile(message.getClientId(), SalesClientConstants.FILE_PATH,
					SalesClientConstants.FILE_NAME);
		}
		if (message.getMessage() != null && message.getMessage().equalsIgnoreCase(SalesClientConstants.CSV_READY)) {
			new SalesRestClientImpl().viewSalesData(message.getClientId());
		}

	}

	@OnClose
	public void onClose(final Session session) {
		log.info("client closed ");

	}

}
