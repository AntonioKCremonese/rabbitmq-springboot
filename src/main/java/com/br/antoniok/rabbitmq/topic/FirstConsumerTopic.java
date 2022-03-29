package com.br.antoniok.rabbitmq.topic;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FirstConsumerTopic {
    private static String EXCHANGE_NAME = "topic_HelloWorld_Exchange";
    private static String BINDING_KEY = "*.*.rabbit";

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

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            channel.queueBind(randomQueue, EXCHANGE_NAME, BINDING_KEY);

            DeliverCallback deliveryCallback = (ConsumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Message received by first consumer topic: "+ message);
            };

            channel.basicConsume(randomQueue, true, deliveryCallback, ConsumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
