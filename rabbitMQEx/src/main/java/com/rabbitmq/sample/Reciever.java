package com.rabbitmq.sample;

import java.io.IOException;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class Reciever {

	  private final static String QUEUE_NAME = "hello";

	  private static Channel setupChannel() {
		  ConnectionFactory connFactory= new ConnectionFactory();
		  connFactory.setHost("localhost");
		  Channel channel=null;
		  try {
			Connection conn=connFactory.newConnection();
			channel=conn.createChannel();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		  return channel;
	  }
	public static void main(String[] args) {
		Channel channel=setupChannel();
		try {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		    DeliverCallback deliverCallback = (consumerTag, deliver) -> {
		    	String message=new String(deliver.getBody(), "UTF-8");
		    	Sender.doWork(message);
		    	channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
		        System.out.println(" [x] Received '" + message + "'");		    	
		    };
		    channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
