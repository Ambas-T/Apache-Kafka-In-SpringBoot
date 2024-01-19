package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.exception.OrderException;
import com.example.orderservice.kafka.OrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderProducer orderProducer;

    private static final String topic = "order.create.response.topic";

    public void sendOrder(OrderDTO order) throws OrderException {
        try{
            orderProducer.sendOrderResponse(topic, order);
            log.info("Order -> {} is published to Kafka.", order);
        } catch (Exception e){
            log.error("Exception while publishing order -> ", e);
            throw new OrderException("Exception occur.");
        }
    }
}
