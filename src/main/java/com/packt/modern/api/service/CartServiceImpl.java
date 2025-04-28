package com.packt.modern.api.service;

import com.packt.modern.api.entity.CartEntity;
import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.model.Item;
import com.packt.modern.api.repository.CartRepository;
import com.packt.modern.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Autowired
    public CartServiceImpl(final CartRepository cartRepository,
                           final UserRepository userRepository,
                           final ItemService itemService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
    }
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
