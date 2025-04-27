package com.packt.modern.api.service;

import com.packt.modern.api.entity.CartEntity;
import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public List<Item> addItemsByCustomerId(String customerId, Item item) {
        return null;
    }

    @Override
    public List<Item> addOrReplaceItemsByCustomerId(String customerId, Item item) {
        return null;
    }

    @Override
    public void deleteCartByCustomerId(String customerId) {

    }

    @Override
    public void deleteItemFromCart(String customerId, String itemId) {

    }

    @Override
    public CartEntity getCartByCustomerId(String customerId) {
        return null;
    }

    @Override
    public List<ItemEntity> getCartItemsByCustomerId(String customerId) {
        return null;
    }

    @Override
    public Item getCartItemByItemAndCustomerId(String customerId, String itemId) {
        return null;
    }
}
