package com.packt.modern.api.service;

import com.packt.modern.api.entity.AuthorizationEntity;
import com.packt.modern.api.entity.OrderEntity;
import com.packt.modern.api.model.PaymentReq;
import com.packt.modern.api.repository.OrderRepository;
import com.packt.modern.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(final PaymentRepository paymentRepository,
                              final OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<AuthorizationEntity> authorize(PaymentReq paymentReq) {
        return Optional.empty();
    }

    @Override
    public Optional<AuthorizationEntity> getOrdersPaymentAuthorization(String orderId) {
        return orderRepository.findById(UUID.fromString(orderId)).map(OrderEntity::getAuthorizationEntity);
    }
}
