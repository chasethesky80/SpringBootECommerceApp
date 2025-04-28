package com.packt.modern.api.service;

import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.entity.ProductEntity;
import com.packt.modern.api.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public ItemEntity toEntity(Item item) {
        return new ItemEntity().setProduct(
            new ProductEntity().setId(UUID.fromString(item.getId())))
        .setQuantity(item.getQuantity())
        .setPrice(item.getUnitPrice());
    }

    @Override
    public List<ItemEntity> toEntityList(List<Item> itemList) {
        if (Objects.isNull(itemList)) {
            return List.of();
        }
        return itemList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Item toModel(ItemEntity itemEntity) {
        return new Item().quantity(itemEntity.getQuantity()).unitPrice(
                itemEntity.getPrice()).id(itemEntity.getProduct().getId().toString());
    }

    @Override
    public List<Item> toModelList(List<ItemEntity> itemEntities) {
        return itemEntities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
