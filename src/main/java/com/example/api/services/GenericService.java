package com.example.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<T> {

    T save(T t);

    T update(Long numero, T t);

    void delete(Long numero);

    Page<T> findFilter(T filtro, Pageable pageable);

}
