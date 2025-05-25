package com.packt.modern.api.hateos;

import com.packt.modern.api.controller.AddressController;
import com.packt.modern.api.entity.AddressEntity;
import com.packt.modern.api.model.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressRepresentationalModelAssembler extends RepresentationModelAssemblerSupport<AddressEntity, Address> {
    public AddressRepresentationalModelAssembler() {
        super(AddressController.class, Address.class);
    }


    @Override
    public Address toModel(AddressEntity entity) {
        Address model = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, model);
        model.id(entity.getId().toString());
        try {
            model.add(linkTo(methodOn(AddressController.class).getAddressesById(entity.getId().toString()))
                    .withSelfRel());
            return model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Coverts the collection of Address entities to list of resources.
     *
     * @param entities
     */
    public List<Address> toListModel(List<AddressEntity> entities) {
        if (Objects.isNull(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
