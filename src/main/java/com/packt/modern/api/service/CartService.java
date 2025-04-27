package com.packt.modern.api.service;

import com.packt.modern.api.entity.CartEntity;
import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.model.Item;
import jakarta.validation.Valid;

import java.util.List;

public interface CartService {

    List<Item> addItemsByCustomerId(final String customerId, @Valid Item item);

    List<Item> addOrReplaceItemsByCustomerId(final String customerId, @Valid Item item);

    void deleteCartByCustomerId(final String customerId);

    void deleteItemFromCart(final String customerId, final String itemId);

    CartEntity getCartByCustomerId(final String customerId);

    List<ItemEntity> getCartItemsByCustomerId(final String customerId);

    Item getCartItemByItemAndCustomerId(final String customerId, final String itemId);
}
