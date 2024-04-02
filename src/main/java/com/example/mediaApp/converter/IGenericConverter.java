package com.example.mediaApp.converter;

public interface IGenericConverter<E, D> {
    D convertFromEntityToDTO(E entity);
}
