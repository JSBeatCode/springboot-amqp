package com.work.amqpboot.app.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.support.ConsumerTagStrategy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RecvReceiveLogsTopic {

	private static final String EXCHANGE_NAME = "topic_logs";

	public static void execute(String[] argv) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();

		if (argv.length < 1) {
			System.err.println(" Usage : ReceiveLogsTopic [binding_key]...");
		}

		for (String bindingKey : argv) {
			channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
		}

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallBack = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "' : '" + message + "'");
		};

		channel.basicConsume(queueName, true, deliverCallBack, ConsumerTagStrategy -> {
		});
	}
}