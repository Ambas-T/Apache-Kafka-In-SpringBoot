package com.example.shippingservice.kafka;

import com.example.shippingservice.dto.OrderDTO;
import com.example.shippingservice.exception.ShippingException;
import com.example.shippingservice.service.ShippingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShippingConsumer {

    @Autowired
    private ShippingService shippingService;

    private final ObjectMapper objectMapper;
    private final Gson gson;

    public ShippingConsumer() {
        this.objectMapper = new ObjectMapper();
        this.gson = new Gson();
    }

    @KafkaListener(topics = "order.create.response.topic", groupId = "ordersgroup")
    public void processI0139ResponseFromSAP(ConsumerRecord<String, String> consumerRecord) throws ShippingException {
        try {
            log.info("Consumed record with offset {}: {}", consumerRecord.offset(), consumerRecord.value());

            OrderDTO response = parseOrderResponse(consumerRecord.value());
            shippingService.saveOrderToDB(response);
        } catch (RuntimeException | JsonProcessingException e) {
            log.error("Error processing record with offset {}: {}", consumerRecord.offset(), e.getMessage(), e);
            throw new ShippingException("Exception occur");
        }
    }

    private OrderDTO parseOrderResponse(String json) throws JsonProcessingException {
        OrderDTO response = gson.fromJson(json, OrderDTO.class);
        log.info("Response from OrderService: {}", response);
        return response;
    }
}
