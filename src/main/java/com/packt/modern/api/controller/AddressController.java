package com.packt.modern.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.packt.modern.api.AddressApi;
import com.packt.modern.api.entity.AddressEntity;
import com.packt.modern.api.hateos.AddressRepresentationalModelAssembler;
import com.packt.modern.api.model.AddAddressReq;
import com.packt.modern.api.model.Address;
import com.packt.modern.api.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class AddressController implements AddressApi {
    private final AddressService addressService;
    private final AddressRepresentationalModelAssembler addressRepresentationalModelAssembler;

    @Autowired
    public AddressController(AddressService addressService, AddressRepresentationalModelAssembler addressRepresentationalModelAssembler) {
        this.addressService = addressService;
        this.addressRepresentationalModelAssembler = addressRepresentationalModelAssembler;
    }

    @Override
    public ResponseEntity<Address> createAddress(@Valid AddAddressReq addAddressReq) throws JsonProcessingException {
        return status(HttpStatus.CREATED)
                .body(addressService.createAddress(addAddressReq).map(addressRepresentationalModelAssembler::toModel)
                .get());

    }

    @Override
    public ResponseEntity<Void> deleteAddressesById(String id) {
        addressService.deleteAddressesById(id);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Address> getAddressesById(String id) {
        return addressService.getAddressesById(id).map(addressRepresentationalModelAssembler::toModel)
                .map(ResponseEntity::ok).orElse(notFound().build());
    }

    @Override
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ok(addressRepresentationalModelAssembler.toListModel((List<AddressEntity>) addressService.getAllAddresses()));
    }

}
