package com.work.amqpboot.app.basic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	
private final static String QUEUE_NAME = "hello";
	
	public static void execute() throws IOException, TimeoutException {
		// rabbitmq amqp 연결
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		try (
				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()
				) {
			// 보낼 큐 정의
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			// 큐에 메세지 전송
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			System.out.println("[x] Sent message = " + message);
		}
	}
}
