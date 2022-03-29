package com.br.antoniok.rabbitmq.fanOut;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerFanOut {
    private static String EXCHANGE_NAME = "fanout_HelloWorld_Exchange";

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

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueBind(randomQueue, EXCHANGE_NAME, "");

            DeliverCallback deliveryCallback = (ConsumerTag, delivery) -> {
                Order orderReceived = (Order) SerializationUtils.deserialize(delivery.getBody());
                System.out.println("Message received by first consumer "+orderReceived.toString());
            };

            channel.basicConsume(randomQueue, true, deliveryCallback, ConsumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
