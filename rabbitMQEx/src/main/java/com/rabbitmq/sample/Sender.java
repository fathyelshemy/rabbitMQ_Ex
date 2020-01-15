package com.rabbitmq.sample;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Sender {

	  private final static String QUEUE_NAME = "hello";

	public static Channel setUpChanel() {
		ConnectionFactory connFactory= new ConnectionFactory();
		connFactory.setHost("localhost");
		Channel channel=null;
		try {
			Connection connection= connFactory.newConnection();
			 channel=connection.createChannel();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		return channel;
	}
	
	public static void sendToQueue(String inMessag, Channel channel) {
		doWork(inMessag);
		try {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = inMessag;
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Channel channel= setUpChanel();
		List<String>inMessage=Arrays.asList("Hello","Hello.","Hello..");
		for(String message:inMessage) {
			sendToQueue(message,channel);
		}
		
	}

	public static void doWork(String inWord) {
		for(char c: inWord.toCharArray()) {
			if(c=='.')
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}
