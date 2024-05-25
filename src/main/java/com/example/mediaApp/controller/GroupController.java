package com.example.mediaApp.controller;

import com.example.mediaApp.converter.AppUserConverter;
import com.example.mediaApp.converter.GroupConverter;
import com.example.mediaApp.converter.GroupRequestConverter;
import com.example.mediaApp.converter.PostConverter;
import com.example.mediaApp.model.dto.GroupDTO;
import com.example.mediaApp.model.entity.GroupEntity;
import com.example.mediaApp.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class GroupController {

    private final GroupService service;
    private final AppUserConverter appUserConverter;
    private final PostConverter postConverter;
    private final GroupRequestConverter groupRequestConverter;
    private static GroupConverter groupConverter;

    @PostMapping
    public ResponseEntity<GroupDTO> create(@RequestBody GroupDTO group){
        GroupEntity newGroupEntity = service.create(group);
        return ResponseEntity.ok(getGroupConverter().convertFromEntityToDTO(newGroupEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO group){
        Optional<GroupEntity> updatedEntity = service.updateById(id, group);
        return updatedEntity.map( entity ->
                ResponseEntity.ok(getGroupConverter().convertFromEntityToDTO(entity))
        )
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private GroupConverter getGroupConverter(){
        if (groupConverter == null)
            groupConverter = new GroupConverter(
                    appUserConverter,
                    postConverter,
                    groupRequestConverter);
        return groupConverter;
    }
}
