package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.model.dto.GroupDTO;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.GroupEntity;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.repository.AppUserRepository;
import com.example.mediaApp.repository.GroupRepository;
import com.example.mediaApp.repository.GroupRequestRepository;
import com.example.mediaApp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repository;
    private final AppUserRepository appUserRepository;
    private final GroupRequestRepository groupRequestRepository;
    private final PostRepository postRepository;

    public GroupEntity find(long id){
        return repository
                .findById(id)
                .orElse(null);
    }

    public List<GroupEntity> getAll() {
        return repository.findAll();
    }

    public List<GroupEntity> getAllGroupByAdminUserId(Long userId) {
        return repository.findGroupEntitiesByAdminUser_Id(userId);
    }

    public List<GroupEntity> getAllGroupByParticipantUserId(Long userId) {
        AppUserEntity userEntity = appUserRepository.getReferenceById(userId);
        return repository.findGroupEntitiesByParticipantUsersContaining(userEntity);
    }

    public Optional<GroupEntity> getById(Long id) {
        return repository.findById(id);
    }

    public GroupEntity create(GroupDTO groupDTO) {
        GroupEntity groupEntity = new GroupEntity();

        updateEntityWithValuesFromDto(groupDTO, groupEntity);

        if (groupDTO.getParticipantUsers() != null)
            for (AppUserDTO user : groupDTO.getParticipantUsers()){
                AppUserEntity userEntity = appUserRepository.getReferenceById(user.getId());
                userEntity.getJoinedGroups().add(groupEntity);
            }

        if (groupDTO.getPosts() != null)
            for (PostDTO post : groupDTO.getPosts()){
                PostEntity postEntity = postRepository.getReferenceById(post.getId());
                postEntity.setGroup(groupEntity);
            }

        repository.save(groupEntity);

        return groupEntity;
    }

    public Optional<GroupEntity> updateById(Long id, GroupDTO group) {
        if (repository.existsById(id) &&
            appUserRepository.existsById(group.getAdminUser().getId())){
            GroupEntity groupEntity = repository.getReferenceById(id);

            updateEntityWithValuesFromDto(group, groupEntity);

            repository.save(groupEntity);

            return Optional.of(groupEntity);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateEntityWithValuesFromDto(GroupDTO newGroup, GroupEntity updatableGroup) {
        updatableGroup.setName(newGroup.getName());
        updatableGroup.setDescription(newGroup.getDescription());
        updatableGroup.setImage(newGroup.getImage());
        updatableGroup.setAdminUser(appUserRepository.getReferenceById(newGroup.getAdminUser().getId()));
        updatableGroup.setParticipantUsers(
                newGroup.getParticipantUsers() != null
                        ? newGroup.getParticipantUsers().stream()
                            .map(parti -> appUserRepository.getReferenceById(parti.getId()))
                            .toList()
                        : new ArrayList<>()
        );
        updatableGroup.setGroupRequests(
                newGroup.getGroupRequests() != null
                        ? newGroup.getGroupRequests().stream()
                            .map(req -> groupRequestRepository.getReferenceById(req.getId()))
                            .toList()
                        : new ArrayList<>()
        );
        updatableGroup.setPosts(
                newGroup.getPosts() != null
                        ? newGroup.getPosts().stream()
                            .map(post -> postRepository.getReferenceById(post.getId()))
                            .toList()
                        : new ArrayList<>()
        );
    }
}
