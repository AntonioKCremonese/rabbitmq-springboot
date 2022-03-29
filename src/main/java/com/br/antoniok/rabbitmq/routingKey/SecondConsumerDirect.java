package com.br.antoniok.rabbitmq.routingKey;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SecondConsumerDirect {
    private static String EXCHANGE_NAME = "direct_HelloWorld_Exchange";
    private static String ROUTING_KEY_BINDING = "routing_key_b";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            String randomQueue = channel.queueDeclare().getQueue();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueBind(randomQueue, EXCHANGE_NAME, ROUTING_KEY_BINDING);

            DeliverCallback deliveryCallback = (ConsumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Message received by second consumer "+ message);
            };

            channel.basicConsume(randomQueue, true, deliveryCallback, ConsumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
