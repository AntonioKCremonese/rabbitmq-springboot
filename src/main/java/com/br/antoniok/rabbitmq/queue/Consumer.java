package com.br.antoniok.rabbitmq.queue;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static String NAME_QUEUE = "HELLO WORLD";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(NAME_QUEUE,false, false,false, null);

            DeliverCallback deliveryCallback = (ConsumerTag, delivery) -> {
                Order orderReceived = (Order) SerializationUtils.deserialize(delivery.getBody());
                System.out.println("Message received "+orderReceived.toString());
            };

            channel.basicConsume(NAME_QUEUE, true, deliveryCallback, ConsumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
