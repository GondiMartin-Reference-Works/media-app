package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.AddressDTO;
import com.example.mediaApp.model.entity.AddressEntity;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.repository.AddressRepository;
import com.example.mediaApp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final AppUserRepository appUserRepository;

    public List<AddressEntity> getAll(Long userId){
        Optional<AppUserEntity> appUserEntity = appUserRepository.findById(userId);
        return appUserEntity.map(AppUserEntity::getAddresses)
                .orElseGet(ArrayList::new);
    }

    public AddressEntity create(AddressDTO addressDTO, Long userId){
        AddressEntity addressEntity = new AddressEntity();

        updateEntityWithValuesFromDto(addressDTO, addressEntity);

        AppUserEntity appUserEntity = appUserRepository.getReferenceById(userId);
        appUserEntity.getAddresses().add(addressEntity);

        repository.save(addressEntity);

        return addressEntity;
    }

    public Optional<AddressEntity> update(Long id, AddressDTO addressDTO, Long userId) {
        if (repository.existsById(id) && appUserRepository.existsById(userId)){
            AddressEntity addressEntity = repository.getReferenceById(id);
            AppUserEntity appUserEntity = appUserRepository.getReferenceById(userId);
            int indexOfAddress = appUserEntity.getAddresses().indexOf(addressEntity);

            if (indexOfAddress == -1)
                return Optional.empty();

            updateEntityWithValuesFromDto(addressDTO, addressEntity);

            appUserEntity.getAddresses().set(indexOfAddress, addressEntity);

            return Optional.of(
                    repository.save(addressEntity)
            );
        }

        return Optional.empty();
    }

    public void delete(Long id, Long userId) {
        if (repository.existsById(id) && appUserRepository.existsById(userId)){
            AddressEntity addressEntity = repository.getReferenceById(id);
            AppUserEntity appUserEntity = appUserRepository.getReferenceById(userId);
            int indexOfAddress = appUserEntity.getAddresses().indexOf(addressEntity);

            if (indexOfAddress != -1){
                appUserEntity.getAddresses().remove(addressEntity);
                repository.delete(addressEntity);
            }
        }
    }

    private void updateEntityWithValuesFromDto(AddressDTO newAddress, AddressEntity updatableAddress){
        updatableAddress.setCountry(newAddress.getCountry());
        updatableAddress.setCity(newAddress.getCity());
        updatableAddress.setZipCode(newAddress.getZipCode());
        updatableAddress.setStreet(newAddress.getStreet());
        updatableAddress.setHouseNum(newAddress.getHouseNum());
    }
}