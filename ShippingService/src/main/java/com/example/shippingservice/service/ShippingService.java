package com.example.shippingservice.service;

import com.example.shippingservice.dto.OrderDTO;
import com.example.shippingservice.entity.Ship;
import com.example.shippingservice.exception.ShippingException;
import com.example.shippingservice.repository.ShippingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Transactional
    public void saveOrderToDB(OrderDTO order) throws ShippingException {
        try {
            Ship ship = new Ship();

            ship.setOrderId(order.getId());
            ship.setCustomerName(order.getCustomerName());
            ship.setProductDetails(order.getProductDetails());

            shippingRepository.save(ship);
            log.info("Order -> {} saved to database.", order);
        }catch (Exception e){
            log.error("Exception occur while saving data to Ship DB -> ", e);
            throw new ShippingException("Exception occur while saving data to ship DB");
        }
    }
}
