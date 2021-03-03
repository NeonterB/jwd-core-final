package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.exception.EntityCollisionException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.io.ReadStrategy;
import com.epam.jwd.core_final.io.WriteStrategy;
import com.epam.jwd.core_final.repository.EntityRepository;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public class EntityRepositoryImpl implements EntityRepository {
    private static final EntityRepositoryImpl instance = new EntityRepositoryImpl();

    private static final Logger logger = LoggerFactory.getLogger(EntityRepositoryImpl.class);

    private EntityRepositoryImpl() {
    }

    public static EntityRepositoryImpl getInstance() {
        return instance;
    }

    private <T extends AbstractBaseEntity> Class<?> getStrategyImplementation(Class<?> strategyInterface, Class<T> entityClass) throws UnknownEntityException {
        if (!(strategyInterface.equals(WriteStrategy.class) || strategyInterface.equals(ReadStrategy.class))) {
            throw new IllegalArgumentException("Unknown strategy class");
        }
        Reflections reflections = new Reflections("com.epam.jwd.core_final.io.impl");
        return reflections.getSubTypesOf(strategyInterface).stream()
                .filter(aClass -> {
                    Type[] types = aClass.getGenericInterfaces();
                    Type type = Arrays.stream(types).filter(t -> {
                        if (t instanceof ParameterizedType) {
                            return ((ParameterizedType) t).getRawType().equals(strategyInterface);
                        }
                        return false;
                    }).findFirst().get();

                    if (type instanceof ParameterizedType) {
                        ParameterizedType pType = (ParameterizedType) type;
                        return pType.getActualTypeArguments().length == 1 && pType.getActualTypeArguments()[0].equals(entityClass);
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(() -> new UnknownEntityException(entityClass.getSimpleName()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractBaseEntity> Collection<T> getAll(Class<T> tClass) throws IOException {
        Collection<T> collection = null;
        try {
            ReadStrategy<T> strategy = (ReadStrategy<T>) getStrategyImplementation(ReadStrategy.class, tClass).newInstance();
            collection = strategy.readEntities();
        } catch (UnknownEntityException | IllegalAccessException | InstantiationException e) {
            logger.error(e.getMessage());
        }
        logger.debug("{} entities were read from file", tClass);
        return collection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractBaseEntity> void create(T entity) throws EntityCollisionException, IOException {
        ApplicationContext context = Main.getApplicationMenu().getApplicationContext();

        Collection<T> cache = (Collection<T>) context.retrieveBaseEntityList(entity.getClass());
        if (cache.contains(entity))
            throw new EntityCollisionException(entity.getClass().getSimpleName(), entity.toString() + " already exists");

        cache = (Collection<T>) context.updateCache(entity.getClass());
        if (cache.contains(entity))
            throw new EntityCollisionException(entity.getClass().getSimpleName(), entity.toString() + " already exists");

        try {
            WriteStrategy<T> strategy = (WriteStrategy<T>) getStrategyImplementation(WriteStrategy.class, entity.getClass()).newInstance();
            strategy.writeEntity(entity);
        } catch (UnknownEntityException | InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        logger.debug("{} was written to file", entity);
    }

    @Override
    public <T extends AbstractBaseEntity> void delete(T entity) {

    }
}
