package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.EntityCollisionException;

import java.io.IOException;
import java.util.Collection;

public interface EntityRepository {
    <T extends AbstractBaseEntity> Collection<T> getAll(Class<T> tClass) throws IOException;

    <T extends AbstractBaseEntity> void create(T entity) throws EntityCollisionException, IOException;

    <T extends AbstractBaseEntity> void delete(T entity);
}
