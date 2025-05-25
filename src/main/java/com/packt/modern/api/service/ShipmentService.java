package com.packt.modern.api.service;

import com.packt.modern.api.entity.ShipmentEntity;
import jakarta.validation.constraints.Min;

public interface ShipmentService {

    Iterable<ShipmentEntity> getShipmentByOrderId(@Min(value = 1L, message="Invalid order id") String orderId);
}
