package com.packt.modern.api.service;

import com.packt.modern.api.entity.CardEntity;
import com.packt.modern.api.model.AddCardReq;

import java.util.Optional;

public interface CardService {

    void deleteCardById(final String id);

    Iterable<CardEntity> getAllCards();

    Optional<CardEntity> getCardById(final String cardId);

    Optional<CardEntity> registerCard(final AddCardReq addCardReq);
}
