package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.Collection;

public interface ApplicationContext {

    <T extends AbstractBaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass);

    void init() throws InvalidStateException;

    /**
     * updates and returns cache
     * @param tClass Entity class
     * @param <T> Entity type
     * @return updated cache
     */
    <T extends AbstractBaseEntity> Collection<T> updateCache(Class<T> tClass);
}
