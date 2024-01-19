package com.example.orderservice.kafka;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.exception.OrderException;
import com.example.orderservice.util.ObjectMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderResponse(String topic, OrderDTO order) throws OrderException {

        try {
            ObjectMapperUtil<OrderDTO> mapperUtil = new ObjectMapperUtil<>();
            String orderToString = mapperUtil.objectToString(order);

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, orderToString);

            CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(record).completable();

            future.thenApply(SendResult::getRecordMetadata)
                    .thenAccept(metadata -> log.info("Successfully sent message to topic {}: offset: {}", metadata.topic(), metadata.offset()))
                    .exceptionally(ex -> {
                        log.error("Failed to send message", ex);
                        return null;
                    });
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException occur while sending message -> ", e);
            throw new OrderException("JsonProcessingException");
        }catch (Exception e){
            log.error("Unexpected error occur while sending message -> ", e);
            throw new OrderException("Internal Server Error");
        }
    }
}