package com.example.mediaApp.model.enums.converter;

import com.example.mediaApp.model.enums.PostLike;
import org.springframework.core.convert.converter.Converter;

public class PostLikeEnumConverter implements Converter<String, PostLike> {
    @Override
    public PostLike convert(String source) {
        try {
            return PostLike.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PostLike.NUll;
        }
    }
}