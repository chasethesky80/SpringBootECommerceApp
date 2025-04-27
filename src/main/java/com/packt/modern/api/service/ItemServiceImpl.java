package com.packt.modern.api.service;

import com.packt.modern.api.entity.ItemEntity;
import com.packt.modern.api.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public ItemEntity toEntity(Item item) {
        return null;
    }

    @Override
    public List<ItemEntity> toEntityList(List<Item> itemList) {
        return null;
    }

    @Override
    public Item toModel(ItemEntity itemEntity) {
        return null;
    }

    @Override
    public List<Item> toModelList(List<ItemEntity> itemEntities) {
        return null;
    }
}
