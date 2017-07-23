package com.rest.sales.client;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.eclipse.jetty.util.component.LifeCycle;

public class ClientStarter {
	private static CountDownLatch latch;

	public static void main(final String[] args) throws Exception {

		// final String clientId = UUID.randomUUID().toString().substring(0, 8);
		latch = new CountDownLatch(1);

		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		final String uri = "ws://localhost:8080/ws";

		try (Session session = container.connectToServer(BroadcastClientEndpoint.class, URI.create(uri))) {
			latch.await();
		}

		if (container instanceof LifeCycle) {
			// ( ( LifeCycle )container ).stop();
		}
		System.in.read();
	}
}
