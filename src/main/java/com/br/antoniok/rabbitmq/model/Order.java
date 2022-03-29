package com.br.antoniok.rabbitmq.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;

public class Order implements Serializable {
    Long id;
    BigDecimal value;
    int quantity;
    String productName;
    BigDecimal totalValue;

    public Order() {}

    public Order(BigDecimal value, int quantity, String productName) {
        this.id = new Random().nextLong();
        this.value = value;
        this.quantity = quantity;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public void sumTotalValue(BigDecimal value, int quantity) {
        BigDecimal totalSum = value.multiply(BigDecimal.valueOf(quantity));
        setTotalValue(totalSum);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", value=" + value +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", totalValue=" + totalValue +
                '}';
    }
}
