package com.packt.modern.api.service;

import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.model.Item;

import java.util.List;

public interface ItemService {

    ItemEntity toEntity(final Item item);

    List<ItemEntity> toEntityList(final List<Item> itemList);

    Item toModel(final ItemEntity itemEntity);

    List<Item> toModelList(final List<ItemEntity> itemEntities);
}
