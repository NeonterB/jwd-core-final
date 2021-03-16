package com.epam.jwd.core_final.repository.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.io.EraseStrategy;
import com.epam.jwd.core_final.io.ReadStrategy;
import com.epam.jwd.core_final.io.WriteStrategy;
import com.epam.jwd.core_final.repository.EntityRepository;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class EntityRepositoryImpl implements EntityRepository {
    private static final EntityRepositoryImpl instance = new EntityRepositoryImpl();

    private static final Logger logger = LoggerFactory.getLogger(EntityRepositoryImpl.class);

    private EntityRepositoryImpl() {
    }

    public static EntityRepositoryImpl getInstance() {
        return instance;
    }

    private <T extends BaseEntity> Class<?> getStrategyImplementation(Class<?> strategyInterface, Class<T> entityClass) throws UnknownEntityException {
        assert (strategyInterface.equals(WriteStrategy.class) ||
                strategyInterface.equals(ReadStrategy.class) ||
                strategyInterface.equals(EraseStrategy.class));

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

//    public <T extends BaseEntity> LocalDateTime getLastModifiedTime(Class<T> tClass) throws IOException, IllegalAccessException {
//        URL path = null;
//        if (tClass.equals(CrewMember.class)) {
//            path = getClass().getClassLoader().getResource(ApplicationProperties.getInstance().getCrewFileDir());
//        } else if (tClass.equals(Spaceship.class)) {
//            path = getClass().getClassLoader().getResource(ApplicationProperties.getInstance().getSpaceshipFileDir());
//        } else throw new IllegalAccessException(tClass.getSimpleName() + " is not for modifying");
//        File file = null;
//        try {
//            assert path != null;
//            file = new File(path.toURI());
//        } catch (URISyntaxException e) {
//            logger.error(e.getMessage());
//        }
//        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
//        return LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), ZoneId.systemDefault());
//    }

    @Override
    public <T extends BaseEntity> Collection<T> getAll(Class<T> tClass) throws IOException {
        Collection<T> collection = null;
        try {
            ReadStrategy<T> strategy = (ReadStrategy<T>) getStrategyImplementation(ReadStrategy.class, tClass).newInstance();
            collection = strategy.readEntities();
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error(e.getMessage());
        }
        logger.debug("{} entities were read from file", tClass);
        return collection;
    }

    @Override
    public <T extends BaseEntity> void create(T entity) throws IOException {
        assert entity.getClass().equals(CrewMember.class) || entity.getClass().equals(Spaceship.class);
        try {
            WriteStrategy<T> strategy = (WriteStrategy<T>) getStrategyImplementation(WriteStrategy.class, entity.getClass()).newInstance();
            strategy.writeEntity(entity);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        logger.debug("{} was written to file", entity);
    }

    @Override
    public <T extends BaseEntity> void delete(T entity) throws IOException, EntityNotFoundException {
        try {
            EraseStrategy<T> strategy = (EraseStrategy<T>) getStrategyImplementation(EraseStrategy.class, entity.getClass()).newInstance();
            strategy.erase(entity);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        logger.debug("{} was deleted from file", entity);
    }

}
