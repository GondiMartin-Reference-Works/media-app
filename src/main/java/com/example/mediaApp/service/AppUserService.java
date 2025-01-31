package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private static final Logger LOGGER = LogManager.getLogger(AppUserService.class);

    public List<AppUserEntity> getAll(){
        return repository.findAll();
    }

    public List<AppUserEntity> getAllWithIsFriend(String email){
        Optional<AppUserEntity> maybeCurrentUser = repository.findByEmail(email);
        List<AppUserEntity> allUsers = repository.findAll();
        if(maybeCurrentUser.isEmpty()){
            LOGGER.warn("Error querying user with email \"{}\"", email);
            return List.of();
        }
        return allUsers;
    }

    public Optional<AppUserEntity> getEntityByEmail(String email){
        return repository.findByEmail(email);
    }

    public Optional<AppUserEntity> changeUser(long id, AppUserEntity newUser){
        newUser.setId(id);
        return Optional.of(repository.save(newUser));
    }

    public AppUserEntity find(Long id){
        if(id == null){
            LOGGER.error("Id is null.");
            return null;
        }
        if(!repository.existsById(id)){
            LOGGER.error("No user was found with ID: {}", id);
            return null;
        }
        return repository
                .findById(id)
                .orElse(null);
    }

    public Optional<AppUserEntity> updateUser(Long id, AppUserDTO user) {
        if (repository.existsById(id)){
            AppUserEntity appUserEntity = repository.getReferenceById(id);

            appUserEntity.setFirstName(user.getFirstName());
            appUserEntity.setLastName(user.getLastName());
            appUserEntity.setBirthDate(user.getBirthDate());
            appUserEntity.setEmail(user.getEmail());
            appUserEntity.setProfilePicture(user.getProfilePicture());

            return Optional.of(
                    repository.save(appUserEntity)
            );
        }

        return Optional.empty();
    }
}
