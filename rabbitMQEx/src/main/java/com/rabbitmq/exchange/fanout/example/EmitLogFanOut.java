package com.rabbitmq.exchange.fanout.example;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.util.RabbitHelper;

public class EmitLogFanOut {

	private static final String EXCHANGE_NAME = "logs";
	private static final String FAN_OUT = "fanout";

	public static void emitMessage(Channel channel, String message) {
		try {

			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		RabbitHelper rabbitHelper = new RabbitHelper();
		Channel channel = rabbitHelper.setUpChannel();
		rabbitHelper.setUpExchage(channel, EXCHANGE_NAME, FAN_OUT);
		emitMessage(channel, "message");

	}

}
