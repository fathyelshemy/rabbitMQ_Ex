package com.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitHelper {

	public  Channel setUpChannel() {
		ConnectionFactory connFactory= new ConnectionFactory();
		connFactory.setHost("localhost");
		Channel channel=null;
		try {
			Connection conn= connFactory.newConnection();
			channel=conn.createChannel();
			
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		return channel;
	}
	
	public  Channel setUpExchage(Channel channel,String exchangeName, String exchangeType) {
		try {
			channel.exchangeDeclare(exchangeName, exchangeType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return channel;
	}
}
