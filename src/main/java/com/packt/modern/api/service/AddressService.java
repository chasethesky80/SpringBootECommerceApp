package com.packt.modern.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.packt.modern.api.entity.AddressEntity;
import com.packt.modern.api.model.AddAddressReq;

import java.util.Optional;

public interface AddressService {

    Optional<AddressEntity> createAddress(AddAddressReq addAddressReq) throws JsonProcessingException;

    void deleteAddressesById(String id);

    Optional<AddressEntity> getAddressesById(String id);

    Iterable<AddressEntity> getAllAddresses();
}
