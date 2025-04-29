package com.packt.modern.api.service;

import com.packt.modern.api.entity.CardEntity;
import com.packt.modern.api.entity.UserEntity;
import com.packt.modern.api.model.AddCardReq;
import com.packt.modern.api.repository.CardRepository;
import com.packt.modern.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    @Autowired
    public CardServiceImpl(final CardRepository cardRepository,
                           final UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteCardById(String id) {
        cardRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Iterable<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Optional<CardEntity> getCardById(String cardId) {
        return cardRepository.findById(UUID.fromString(cardId));
    }

    @Override
    public Optional<CardEntity> registerCard(AddCardReq addCardReq) {
        CardEntity cardEntity = new CardEntity();
        Optional<UserEntity> userEntityOptional = userRepository.findById(
                UUID.fromString(addCardReq.getUserId()));
        userEntityOptional.ifPresent(cardEntity::setUser);
        return Optional.of(cardEntity.setNumber(addCardReq.getCardNumber())
                .setCvv(addCardReq.getCvv())
                .setExpires(addCardReq.getExpires()));
    }
}
