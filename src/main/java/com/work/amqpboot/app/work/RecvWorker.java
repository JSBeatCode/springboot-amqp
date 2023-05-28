package com.work.amqpboot.app.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RecvWorker {

        private static final String TASK_QUEUE_NAME = "task_queue";
        
        public static void exeucte() throws IOException, TimeoutException {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel();
                
                channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
                System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
                
                channel.basicQos(1); // accept only one unack-ed message at a time (see below)
                
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), "UTF-8");
                        
                        System.out.println(" [x] Received '"+message + "'");
                        try {
                                doWork(message);
                        } finally {
                                System.out.println(" [x] Done");

                                /*
                                 * acknowledged: 소비자는 특정 메시지가 수신, 처리되었으며 RabbitMQ가 이를 삭제할 수 있음을 RabbitMQ에 알림
                                소비자가 ack를 보내지 않고 죽으면(해당 채널이 닫히거나 연결이 닫히거나 TCP 연결이 끊어짐) RabbitMQ는 메시지가 완전히 처리되지 않았음을 이해하고 다시 큐에 넣습니다. 
                                동시에 온라인에 다른 소비자가 있는 경우 다른 소비자에게 빠르게 다시 전달합니다. 
                                이렇게 하면 작업자가 가끔 사망하더라도 메시지가 손실되지 않도록 할 수 있습니다.
                                 * */
                                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); 
                        }
                };
                boolean autoAck = false;
                channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {});
        }

//        메세지 받는 client가 메세지 가지고 마치 일하고 있는 것처럼 보이기 위해 1초의 일하는 시간을 임시로 넣음 
        private static void doWork(String task) {
                for(char ch : task.toCharArray()) {
                        if(ch == '.') {
                                try {
                                        Thread.sleep(1000);
                                } catch (InterruptedException _ignored) {
                                        Thread.currentThread().interrupt();
                                }
                        }
                }
        }
}