package com.br.antoniok.rabbitmq.queue;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

public class Publisher {
    private static String NAME_QUEUE = "HELLO WORLD";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();
            System.out.println(connection.hashCode());
            Channel channel = connection.createChannel();
            channel.queueDeclare(NAME_QUEUE, false, false, false, null);
            Order order = new Order(new BigDecimal(Math.random()).setScale(2, BigDecimal.ROUND_HALF_UP),2,"tomada");
            order.sumTotalValue(order.getValue(), order.getQuantity());

            channel.basicPublish("",NAME_QUEUE,null, SerializationUtils.serialize(order));

            System.out.println("[X] Message sent '" + order.toString() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
