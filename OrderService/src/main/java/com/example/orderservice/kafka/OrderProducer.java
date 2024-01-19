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
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

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

            ListenableFuture<SendResult<String, String>> future = (ListenableFuture<SendResult<String, String>>) this.kafkaTemplate.send(record);

            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("Successfully sent SAP response message to topic {}: {}, offset: {}",
                            result.getRecordMetadata().topic(), order, result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Failed to send SAP response message", ex);
                }
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

