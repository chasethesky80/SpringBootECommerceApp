package com.packt.modern.api.service;

import com.packt.modern.api.entity.AuthorizationEntity;
import com.packt.modern.api.model.PaymentReq;
import jakarta.validation.Valid;

import java.util.Optional;

public interface PaymentService {

    Optional<AuthorizationEntity> authorize(@Valid PaymentReq paymentReq);

    Optional<AuthorizationEntity> getOrdersPaymentAuthorization(final String orderId);
}
