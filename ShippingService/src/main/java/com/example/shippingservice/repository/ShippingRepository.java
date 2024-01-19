package com.example.shippingservice.repository;

import com.example.shippingservice.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Ship, Long> {

}
