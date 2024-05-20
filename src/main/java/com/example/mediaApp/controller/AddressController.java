package com.example.mediaApp.controller;

import com.example.mediaApp.converter.AddressConverter;
import com.example.mediaApp.model.dto.AddressDTO;
import com.example.mediaApp.model.entity.AddressEntity;
import com.example.mediaApp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class AddressController {

    private final AddressService service;
    private final AddressConverter addressConverter;

    @GetMapping()
    public ResponseEntity<List<AddressDTO>> getAll(@RequestParam Long userId){
        return ResponseEntity.ok(service.getAll(userId).stream()
                .map(addressConverter::convertFromEntityToDTO)
                .toList()
        );
    }

    @PostMapping()
    public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO address, @RequestParam Long userId){
        AddressEntity newAddressEntity = service.create(address, userId);
        return ResponseEntity.ok(addressConverter.convertFromEntityToDTO(newAddressEntity));
    }

    @PutMapping("/{id}")
    public HttpStatus put(@PathVariable Long id, @RequestBody AddressDTO address, @RequestParam Long userId){
        Optional<AddressEntity> addressEntity = service.update(id, address, userId);
        return addressEntity.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, @RequestParam Long userId){
        service.delete(id, userId);
    }
}