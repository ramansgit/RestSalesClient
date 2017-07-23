package com.sales.client.test;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.eclipse.jetty.util.component.LifeCycle;

import com.sales.client.ws.SalesWsClient;

public class TestSalesService {
	private static CountDownLatch latch;

	public static void main(final String[] args) throws Exception {

		latch = new CountDownLatch(1);

		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		final String uri = "ws://localhost:8080/ws/sales";

		try (Session session = container.connectToServer(SalesWsClient.class, URI.create(uri))) {
			latch.await();
		}

		if (container instanceof LifeCycle) {
			// ( ( LifeCycle )container ).stop();
		}
		// System.in.read();
	}
}
