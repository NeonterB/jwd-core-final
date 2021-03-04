package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
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
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class NassaContext implements ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(NassaContext.class);

    // no getters/setters for them
    private Collection<EntityWrap<CrewMember>> crewMembers;
    private Collection<EntityWrap<Spaceship>> spaceships;
    private Collection<Planet> planetMap;
    private Collection<FlightMission> missions;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> Collection<? extends BaseEntity> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> requiredCollection = null;
        try {
            Field matchedField = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(field -> {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pType = (ParameterizedType) type;
                            Type[] arguments = pType.getActualTypeArguments();
                            if (pType.getRawType().equals(Collection.class) && arguments.length == 1) {
                                if (arguments[0] instanceof ParameterizedType &&
                                        ((ParameterizedType) arguments[0]).getRawType().equals(EntityWrap.class) &&
                                        ((ParameterizedType) arguments[0]).getActualTypeArguments()[0].equals(tClass)
                                ) {
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
                    .map(EntityWrap::new)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            spaceships = EntityRepositoryImpl.getInstance().getAll(Spaceship.class).stream()
                    .map(EntityWrap::new)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            planetMap = EntityRepositoryImpl.getInstance().getAll(Planet.class);
            missions = new LinkedHashSet<>();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new InvalidStateException("Bad input files", e);
        }
    }

    /**
     * updates cache via repository and returns it
     *
     * @param tClass entity class, which cache requires update
     * @param <T>    entity type
     * @return updated cache
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> void updateCache(Class<T> tClass) {
        assert tClass.equals(CrewMember.class) || tClass.equals(Spaceship.class);

        Collection<EntityWrap<T>> cache = (Collection<EntityWrap<T>>) retrieveBaseEntityList(tClass);
        try {
            cache.removeIf(wrap -> !wrap.isValid());
            Collection<EntityWrap<T>> entityWraps = (Collection<EntityWrap<T>>) EntityRepositoryImpl.getInstance().getAll(tClass)
                    .stream()
                    .map(EntityWrap::new)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            cache.addAll(entityWraps);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.debug("{} cache updated", tClass.getSimpleName());
    }

    public static void main(String[] args) throws InvalidStateException {
        NassaContext context = new NassaContext();
        context.init();
        context.retrieveBaseEntityList(FlightMission.class);
    }
}
