package com.work.amqpboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.work.amqpboot.cloud.JavaAMQPCloud;

@SpringBootApplication
public class AmqpbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmqpbootApplication.class, args);
		
		//cloud amqp 실행
		JavaAMQPCloud.execute();
		
//      basic
//      Recv.execute();
//      Send.execute();
      
//      work
//      RecvWorker.exeucte();
//      String[] recvArgv = {"info","test.task1"};
//      SendNewTask.execute(argv);

//      publish / subscribe
//      String[] argv = {"info","test.task2"};
//      RecvRecieveLogs.execute();
//      SendEmitLog.execute(argv);
      
//      exchange - direct
//      String[] recvArgv = {"info","warning"};
//      RecvReceiveLogs.execute(recvArgv);
//      String[] sendArgv1 = {"info","test.task2"};
//      SendEmitLogDirect.execute(sendArgv1);
//      String[] sendArgv2 = {"warning","test.task1"};
//      SendEmitLogDirect.execute(sendArgv2);
      
//      topic
//      String[] recvArgv = {"info","warning"};
//      RecvReceiveLogsTopic.execute(recvArgv);
//      String[] sendArgv1 = {"info","test.task2"};
//      SendEmitLogTopic.execute(sendArgv1);
//      String[] sendArgv2 = {"warning","test.task1"};
//      SendEmitLogTopic.execute(sendArgv2);
      
	}

}
