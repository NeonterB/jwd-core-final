package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.repository.impl.EntityRepositoryImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class NassaContext implements ApplicationContext {
    public static class EntityWrap<T extends AbstractBaseEntity>{
        private boolean isValid = true;
        private final T entity;

        public EntityWrap(T entity) {
            this.entity = entity;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public boolean isValid() {
            return isValid;
        }

        public T getEntity() {
            return entity;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(NassaContext.class);

    // no getters/setters for them
    private Collection<EntityWrap<CrewMember>> crewMembers;
    private Collection<EntityWrap<Spaceship>> spaceships;
    private Collection<Planet> planetMap;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractBaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> requiredCollection = null;
        try {
            Field matchedField = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(field -> {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pType = (ParameterizedType) type;
                            Type[] arguments = pType.getActualTypeArguments();
                            if (pType.getRawType().equals(Collection.class) && arguments.length == 1){
                                if (((ParameterizedType) arguments[0]).getRawType().equals(EntityWrap.class) && ((ParameterizedType) arguments[0]).getActualTypeArguments()[0].equals(tClass)){
                                    return true;
                                }
                                return arguments[0].equals(tClass);
                            }
                        }
                        return false;
                    })
                    .findFirst()
                    .orElseThrow(() -> new UnknownEntityException(tClass.getSimpleName()));
            matchedField.setAccessible(true);
            requiredCollection = (Collection<T>) matchedField.get(this);
            matchedField.setAccessible(false);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return requiredCollection;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        PropertyReaderUtil.loadProperties();
        try {
            crewMembers = EntityRepositoryImpl.getInstance().getAll(CrewMember.class).stream()
                    .map(EntityWrap::new).collect(Collectors.toSet());
            spaceships = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).stream()
                    .map(EntityWrap::new)
                    .collect(Collectors.toSet());
            planetMap = EntityRepositoryImpl.getInstance().getAll(Planet.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new InvalidStateException("Bad input files", e);
        }
    }

    /**
     * updates cache via repository and returns it
     * @param tClass entity class, which cache requires update
     * @param <T> entity type
     * @return updated cache
     */
    @Override
    public <T extends AbstractBaseEntity> Collection<T> updateCache(Class<T> tClass) {
        Collection<T> cache = retrieveBaseEntityList(tClass);
        try {
            cache.addAll(EntityRepositoryImpl.getInstance().getAll(tClass));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return cache;
    }

    public static void main(String[] args) throws InvalidStateException {
        NassaContext context = new NassaContext();
        context.init();
        context.retrieveBaseEntityList(CrewMember.class);
    }
}
