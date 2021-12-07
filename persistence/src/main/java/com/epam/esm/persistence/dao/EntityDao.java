package com.epam.esm.persistence.dao;

import com.epam.esm.persistence.exception.EntityNotFoundException;

import java.util.List;

public interface EntityDao<E> {
    E create(E entity);

    E find(Long id) throws EntityNotFoundException;

    List<E> findAll();

    void delete(Long id);
}
