package com.packt.modern.api.service;

import com.packt.modern.api.entity.ShipmentEntity;
import com.packt.modern.api.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(final ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Iterable<ShipmentEntity> getShipmentByOrderId(String orderId) {
        return shipmentRepository.findAllById(List.of(UUID.fromString(orderId)));
    }
}
