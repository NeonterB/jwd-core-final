package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.epam.jwd.core_final.util.impl.CrewReader;
import com.epam.jwd.core_final.util.impl.SpaceMapReader;
import com.epam.jwd.core_final.util.impl.SpaceshipReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(NassaContext.class);

    // no getters/setters for them
    private Collection<CrewMember> crewMembers;
    private Collection<Spaceship> spaceships;
    private Collection<Planet> planetMap;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> requiredCollection = null;
        try {
            Field matchedField = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(field -> {
                        Type type = field.getGenericType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pType = (ParameterizedType) type;
                            return pType.getRawType().equals(Collection.class) && pType.getActualTypeArguments().length == 1 && pType.getActualTypeArguments()[0].equals(tClass);
                        }
                        return false;
                    })
                    .findFirst()
                    .orElseThrow(() -> new UnknownEntityException(tClass.getSimpleName()));
            matchedField.setAccessible(true);
            requiredCollection = (Collection<T>) matchedField.get(this);
            matchedField.setAccessible(false);
        } catch (IllegalAccessException e) {
            //todo
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
            crewMembers = new CrewReader().readEntities(
                    getClass().getClassLoader().getResourceAsStream(ApplicationProperties.getInstance().getCrewFileDir())
            );
            spaceships = new SpaceshipReader().readEntities(
                    getClass().getClassLoader().getResourceAsStream(ApplicationProperties.getInstance().getSpaceShipFileDir())
            );
            planetMap = new SpaceMapReader().readEntities(
                    getClass().getClassLoader().getResourceAsStream(ApplicationProperties.getInstance().getSpaceMapFileDir())
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new InvalidStateException("Bad input files", e);
        }
    }

    public static void main(String[] args) throws InvalidStateException {
        NassaContext context = new NassaContext();
        context.init();
    }
}
