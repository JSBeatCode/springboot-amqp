package com.work.amqpboot.cloud;

import java.util.Date;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class JavaAMQPCloud {

	public static void execute() {
		// amqp cloud connection 세팅
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("dingo.rmq.cloudamqp.com");
		connectionFactory.setUsername("qomijvoq");
		connectionFactory.setPassword("PP6w9efekwgunJjLjK7-41I1gJQIOxDy");
		connectionFactory.setVirtualHost("qomijvoq");
		
		// 추가 connection 세팅
		connectionFactory.setRequestedHeartBeat(30);
		connectionFactory.setConnectionTimeout(30000);
		
		// queue, exchange, binding 셋업
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		// queue instance 및 이름 생성
		Queue queue = new Queue("testQueue");
		
		// queue 등록
		admin.declareQueue(queue);
		
		// exchange instance 및 이름 생성
		TopicExchange exchange = new TopicExchange("testExchange");
		
		// exchange 등록
		admin.declareExchange(exchange);
		
		// queue 와 exchange 바인딩
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange)
				// routing key 세팅
				.with("foo.*"));
		
		// listener 셋팅
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		Object listener = new Object() {
			public void handleMessage(String foo) {
				System.out.println("message listener handling: ");
				System.out.println(foo);
			}
		};
		
		// send a message
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		container.setMessageListener(adapter);
		container.setQueueNames("testQueue");
		container.start();
		
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.convertAndSend("testExchange", "foo.bar", "Hello World!");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		container.stop();
		
	}
}
