package com.br.antoniok.rabbitmq.routingKey;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

public class PublisherDirect {
    private static String EXCHANGE_NAME = "direct_HelloWorld_Exchange";
    private static String ROUTING_KEY_A = "routing_key_a";
    private static String ROUTING_KEY_B = "routing_key_b";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            Order order = new Order(new BigDecimal(Math.random()).setScale(2, BigDecimal.ROUND_HALF_UP),2,"interruptor");
            order.sumTotalValue(order.getValue(), order.getQuantity());

            String message = "This is a message to second rounting key";

            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_A,null, SerializationUtils.serialize(order));
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_B, null, message.getBytes());

            System.out.println("[X] Message sent for exchange with routing key '" + order.toString() + "'");
            System.out.println("[X] Second Message sent for exchange with routing key '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
