package com.example.shippingservice.exception;

public class ShippingException extends Exception {

    public ShippingException(String ex) {
        super(ex);
    }

    public ShippingException(String ex, Exception e) {
        super(ex, e);
    }
}
