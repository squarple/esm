package com.epam.esm.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E> {
    E create(E entity);

    Optional<E> find(Long id);

    List<E> findAll();

    void update(E entity);

    void delete(Long id);
}
