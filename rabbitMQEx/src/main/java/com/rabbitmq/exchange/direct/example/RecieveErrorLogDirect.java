package com.rabbitmq.exchange.direct.example;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.util.RabbitHelper;

public class RecieveErrorLogDirect {

	private static final String EXCHANGE_NAME = "directLog";
	private static final String EXCHANGE_TYPE = "direct";

	public static void recieveErrorMessages(Channel channel) {

		try {
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "error");
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			DeliverCallback deliverCallback = (consumerTag, deliver) -> {
				System.out.println(" [x] Received Error'" + new String(deliver.getBody(), "UTF-8") + "'");
			};
			channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void recieveInfoMessages(Channel channel) {

		try {
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "info");
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			DeliverCallback deliverCallback = (consumerTag, deliver) -> {
				System.out.println(" [x] Received Info'" + new String(deliver.getBody(), "UTF-8") + "'");
			};
			channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		RabbitHelper rabbitHelper = new RabbitHelper();

		Channel channel = rabbitHelper.setUpChannel();
		rabbitHelper.setUpExchage(channel, EXCHANGE_NAME, EXCHANGE_TYPE);
		System.out.println("==========error==================");
		recieveErrorMessages(channel);
		System.out.println("===========================================");
		System.out.println("===========================================");

		System.out.println("==========info==================");
		recieveInfoMessages(channel);

	}

}
