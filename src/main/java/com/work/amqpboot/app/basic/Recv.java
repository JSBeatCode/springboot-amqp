package com.work.amqpboot.app.basic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

	private final static String QUEUE_NAME = "hello";
	
	public static void execute() throws IOException, TimeoutException {
		// rabbitmq amqp 연결
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// 받을 큐 정의
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("[*] Waiting for message. To exit press CTRL+C");
		// 큐 메세지 받는 콜백
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("[x] Received = " + message);
		};
		// 큐 메세지 받아서 콜백에 넘기기
		// autoAck = true 면 메세지 받으면 잘 받았다고 confirm 하고 큐에서 메세지 제거. false면 큐에 메세지 그대로 둠.
		// 큐에서 꺼내는 방식은 First In First Out 이기때문에 autoAck을 잘 결정해야함.
		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
	}
	
}
