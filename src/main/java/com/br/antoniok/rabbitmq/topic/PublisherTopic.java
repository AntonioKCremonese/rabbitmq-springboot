package com.br.antoniok.rabbitmq.topic;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

public class PublisherTopic {
    private static String EXCHANGE_NAME = "topic_HelloWorld_Exchange";
    private static String ROUTING_KEY_ORIGIN = "quick.orange.rabbit";
    private static String ROUTING_KEY_A= "quick.rabbit";
    private static String ROUTING_KEY_B = "rabbit.orange";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String originalMessage = "This is a original message to topic";
            String message = "This is a message to quick.rabbit topic";
            String secondMessage = "This is a second message to rabbit.orange topic";

            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_ORIGIN,null, originalMessage.getBytes());
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_A,null, message.getBytes());
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_B, null, secondMessage.getBytes());

            System.out.println("[X] Original message sent for exchange with topic '" + originalMessage + "'");
            System.out.println("[X] Message sent for exchange with topic '" + message + "'");
            System.out.println("[X] Second Message sent for exchange with topic '" + secondMessage + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
