package com.rabbitmq.exchange.direct.example;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.util.RabbitHelper;

public class EmitLogDirect {

	private static final String EXCHANGE_NAME = "directLog";
	private static final String EXCHANGE_TYPE = "direct";

	public static void submitToDirectExchange(Channel channel, String message, String severity) {
		try {
			channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
			System.out.println("[x] send " + severity + " \t message: " + message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RabbitHelper rabbitHelper = new RabbitHelper();
		Channel channel = rabbitHelper.setUpChannel();
		rabbitHelper.setUpExchage(channel, EXCHANGE_NAME, EXCHANGE_TYPE);

		String message = "hello direct";
		String severity = "info";

		submitToDirectExchange(channel, message, severity);
		String message2 = "hell direct";
		String severity2 = "error";
		submitToDirectExchange(channel, message2, severity2);

	}

}
