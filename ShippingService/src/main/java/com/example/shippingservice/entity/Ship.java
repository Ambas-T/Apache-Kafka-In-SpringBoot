package com.example.shippingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ship {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Long orderId;
    private String customerName;
    private String productDetails;
}
