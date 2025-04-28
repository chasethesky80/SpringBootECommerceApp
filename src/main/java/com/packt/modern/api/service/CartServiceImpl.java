package com.packt.modern.api.service;

import com.packt.modern.api.entity.CartEntity;
import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.entity.UserEntity;
import com.packt.modern.api.exceptions.ResourceNotFoundException;
import com.packt.modern.api.model.Item;
import com.packt.modern.api.model.User;
import com.packt.modern.api.repository.CartRepository;
import com.packt.modern.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<Item> addItemsByCustomerId(final String customerId, final Item item) {
        final CartEntity cartEntity = getCartByCustomerId(customerId);
        List<ItemEntity> items = cartEntity.getItems();
        boolean itemExists = items.stream().anyMatch(
                itemEntity -> itemEntity.getId().compareTo(UUID.fromString(item.getId())) == 0);
        if (itemExists) {
            throw new ResourceNotFoundException(String.format("Item with Id (%s) " +
                    "already exists. You can update it.", item.getId()));
        }
        items.add(itemService.toEntity(item));
        return itemService.toModelList(items);
    }

    @Override
    public List<Item> addOrReplaceItemsByCustomerId(String customerId, Item item) {
        final CartEntity cartEntity = getCartByCustomerId(customerId);
        List<ItemEntity> items = cartEntity.getItems();
        boolean itemExists = items.stream().anyMatch(
                itemEntity -> itemEntity.getId().compareTo(UUID.fromString(item.getId())) == 0);
        if (itemExists) {
            items = items.stream().peek(itemEntity -> {
                if (itemEntity.getId().compareTo(UUID.fromString(item.getId())) == 0) {
                    itemEntity.setPrice(item.getUnitPrice());
                    itemEntity.setQuantity(item.getQuantity());
                }
            }).collect(Collectors.toList());
        } else {
            items.add(itemService.toEntity(item));
        }
        return itemService.toModelList(items);
    }

    @Override
    public void deleteCartByCustomerId(String customerId) {
        final CartEntity entity = getCartByCustomerId(customerId);
        cartRepository.deleteById(entity.getId());
    }

    @Override
    public void deleteItemFromCart(String customerId, String itemId) {
        final CartEntity entity = getCartByCustomerId(customerId);
        final List<ItemEntity> items = entity.getItems().stream().filter(
                itemEntity -> !itemEntity.getId().toString().equals(itemId)).toList();
        entity.setItems(items);
    }

    @Override
    public CartEntity getCartByCustomerId(String customerId) {
        final CartEntity cartEntity = cartRepository.findByCustomerId(
                UUID.fromString(customerId)).orElseThrow(() -> new RuntimeException(String.format(
                "Cart does not exist for ID %s", customerId)));
        if (Objects.isNull(cartEntity.getUser())) {
            final UserEntity user = userRepository.findById(
                    UUID.fromString(customerId))
                    .orElseThrow(() -> new RuntimeException(String.format(
                            "Cart does not have user for ID %s", customerId)));
            cartEntity.setUser(user);
        }
        return cartEntity;
    }

    @Override
    public List<Item> getCartItemsByCustomerId(String customerId) {
        final CartEntity entity = getCartByCustomerId(customerId);
        return itemService.toModelList(entity.getItems());
    }

    @Override
    public Item getCartItemByItemAndCustomerId(String customerId, String itemId) {
        final CartEntity entity = getCartByCustomerId(customerId);
        final ItemEntity item = entity.getItems().stream().filter(itemEntity -> itemEntity.getId()
                .toString().equals(itemId)).findFirst().orElse(null);
        return Objects.isNull(item) ? new Item() : itemService.toModel(item);
    }
}
