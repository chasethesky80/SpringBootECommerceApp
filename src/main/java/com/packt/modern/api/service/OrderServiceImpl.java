package com.packt.modern.api.service;

import com.packt.modern.api.entity.OrderEntity;
import com.packt.modern.api.exceptions.ResourceNotFoundException;
import com.packt.modern.api.model.NewOrder;
import com.packt.modern.api.repository.OrderRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderEntity> addOrder(NewOrder newOrder) {
        if (StringUtils.isEmpty(newOrder.getCustomerId())) {
            throw new ResourceNotFoundException("Invalid customer id.");
        }
        if (Objects.isNull(newOrder.getAddress()) || Objects.isNull(newOrder.getAddress().getId())){
            throw new ResourceNotFoundException("Invalid address id.");
        }
        if (Objects.isNull(newOrder.getCard()) || Objects.isNull(newOrder.getCard().getId())){
            throw new ResourceNotFoundException("Invalid card id.");
        }
        return orderRepository.insert(newOrder);
    }

    @Override
    public Iterable<OrderEntity> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(UUID.fromString(customerId));
    }

    @Override
    public Optional<OrderEntity> getByOrderId(String orderId) {
        return orderRepository.findById(UUID.fromString(orderId));
    }
}
