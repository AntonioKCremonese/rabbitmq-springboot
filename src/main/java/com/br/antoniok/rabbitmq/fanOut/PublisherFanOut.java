package com.br.antoniok.rabbitmq.fanOut;

import com.br.antoniok.rabbitmq.model.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

public class PublisherFanOut {
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
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            Order order = new Order(new BigDecimal(Math.random()).setScale(2, BigDecimal.ROUND_HALF_UP),2,"parafuso");
            order.sumTotalValue(order.getValue(), order.getQuantity());

            channel.basicPublish(EXCHANGE_NAME,"",null, SerializationUtils.serialize(order));

            System.out.println("[X] Message sent for exchange '" + order.toString() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
