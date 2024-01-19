package com.example.orderservice.exception;

public class OrderException extends Exception {

    public OrderException(String ex) {
        super(ex);
    }

    public OrderException(String ex, Exception e) {
        super(ex, e);
    }
}
