package com.packt.modern.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.modern.api.entity.AddressEntity;
import com.packt.modern.api.model.AddAddressReq;

import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public Optional<AddressEntity> createAddress(AddAddressReq addAddressReq) throws JsonProcessingException {
        String json = mapper.writeValueAsString(addAddressReq);
        return Optional.of(mapper.readValue(json, AddressEntity.class));
    }

    @Override
    public void deleteAddressesById(String id) {

    }

    @Override
    public Optional<AddressEntity> getAddressesById(String id) {
        return Optional.empty();
    }

    @Override
    public Iterable<AddressEntity> getAllAddresses() {
        return null;
    }
}
