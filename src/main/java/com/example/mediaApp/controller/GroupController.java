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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
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

    @GetMapping()
    public ResponseEntity<List<GroupDTO>> getAll(){
        List<GroupEntity> groupEntities = service.getAll();
        return ResponseEntity.ok(groupEntities.stream()
                .map(getGroupConverter()::convertFromEntityToDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getById(@PathVariable Long id){
        Optional<GroupEntity> groupEntity = service.getById(id);
        return groupEntity.map(entity ->
                ResponseEntity.ok(getGroupConverter().convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<GroupDTO> create(@RequestBody GroupDTO group){
        GroupEntity newGroupEntity = service.create(group);
        return ResponseEntity.ok(getGroupConverter().convertFromEntityToDTO(newGroupEntity));
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
