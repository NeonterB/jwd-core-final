package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.Collection;

public interface EntityRepository {
    <T extends BaseEntity> Collection<T> getAll(Class<T> tClass) throws IOException;

    <T extends BaseEntity> void create(T entity) throws EntityExistsException, IOException;

    <T extends BaseEntity> void delete(T entity) throws IOException, EntityNotFoundException;
}
