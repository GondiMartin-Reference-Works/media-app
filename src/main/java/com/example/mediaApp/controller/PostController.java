package com.example.mediaApp.controller;

import com.example.mediaApp.converter.PostConverter;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class PostController{

    private final PostService service;
    private final PostConverter converter;

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAll(){
        return ResponseEntity.ok(service.getAll()
                .stream().map(converter::convertFromEntityToDTO)
                .toList()
                .reversed()
        );
    }

    @PostMapping()
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO post){
        PostEntity newPostEntity = service.create(post);
        PostDTO newPostDTO = converter.convertFromEntityToDTO(newPostEntity);
        return ResponseEntity.ok(newPostDTO);
    }
}
