package com.rabbitmq.exchange.fanout.example;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.util.RabbitHelper;

public class RecieveLogFanOut {

	private static final String EXCHANGE_NAME = "logs";
	private static final String FAN_OUT = "fanout";

	public static void recieveMessage(Channel channel, String queueName) {
		try {
			channel.queueBind(queueName, EXCHANGE_NAME, "");
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			DeliverCallback deliverCallback = (consumerTag, deliver) -> {
				System.out.println(" [x] Received '" + new String(deliver.getBody(), "UTF-8") + "'");
			};

			channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			RabbitHelper rabbitHelper= new RabbitHelper();
			Channel channel = rabbitHelper.setUpChannel();
			rabbitHelper.setUpExchage(channel, EXCHANGE_NAME, FAN_OUT);
			String queueName = channel.queueDeclare().getQueue();
			System.out.println(queueName);
			recieveMessage(channel, queueName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}
}
