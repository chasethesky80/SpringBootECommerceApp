package com.packt.modern.api.hateos;

import com.packt.modern.api.controller.CartsController;
import com.packt.modern.api.entity.CartEntity;
import com.packt.modern.api.model.Cart;
import com.packt.modern.api.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CartEntity, Cart> {
    private final ItemService itemService;

    public CartRepresentationModelAssembler(final ItemService itemService) {
        super(CartsController.class, Cart.class);
        this.itemService = itemService;
    }

    @Override
    public Cart toModel(CartEntity entity) {
        final String userId = Objects.nonNull(entity.getUser()) ? entity.getUser().getId().toString() : null;
        final String cartId = Objects.nonNull(entity.getId()) ? entity.getId().toString() : null;
        Cart resource = new Cart();
        BeanUtils.copyProperties(entity, resource);
        resource.id(cartId)
                .customerId(userId)
                .items(itemService.toModelList(entity.getItems()));
        try {
            resource.add(linkTo(methodOn(CartsController.class).getCartByCustomerId(userId)).withSelfRel());
            resource.add(linkTo(methodOn(CartsController.class).getCartItemsByCustomerId(userId)).withRel("cart-items"));
            return resource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
