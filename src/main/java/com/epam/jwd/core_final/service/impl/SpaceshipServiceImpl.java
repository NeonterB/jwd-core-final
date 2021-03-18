package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.EntityWrap;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.repository.impl.EntityRepositoryImpl;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "DuplicatedCode"})
public class SpaceshipServiceImpl implements SpaceshipService {
    private static final SpaceshipService instance = SpaceshipServiceProxy.newInstance(new SpaceshipServiceImpl(
            Main.getApplicationMenu().getApplicationContext()
    ));

    private final ApplicationContext context;

    private SpaceshipServiceImpl(ApplicationContext context) {
        this.context = context;
    }

    public static SpaceshipService getInstance() {
        return instance;
    }

    @Override
    public ApplicationContext getContext() {
        return context;
    }

    @Override
    public Collection<Spaceship> findAllSpaceships() {
        context.updateCache(Spaceship.class);
        return ((Collection<EntityWrap<Spaceship>>) context.retrieveBaseEntityList(Spaceship.class)).stream()
                .filter(EntityWrap::isValid)
                .map(EntityWrap::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Spaceship> findAllSpaceshipsByCriteria(Criteria<Spaceship> criteria) {
        Collection<EntityWrap<Spaceship>> cache = (Collection<EntityWrap<Spaceship>>) context.retrieveBaseEntityList(Spaceship.class);
        Collection<Spaceship> foundSpaceships = cache.stream()
                .filter((wrap) -> wrap.isValid() && criteria.meetsCriteria(wrap.getEntity()))
                .map(EntityWrap::getEntity)
                .collect(Collectors.toList());

        //if nothing found - update cache
        if (foundSpaceships.isEmpty()) {
            context.updateCache(Spaceship.class);
            foundSpaceships = cache.stream()
                    .filter((wrap) -> wrap.isValid() && criteria.meetsCriteria(wrap.getEntity()))
                    .map(EntityWrap::getEntity)
                    .collect(Collectors.toList());
        }

        return foundSpaceships;
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<Spaceship> criteria) {
        return findAllSpaceshipsByCriteria(criteria).stream().findAny();
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws EntityExistsException, IOException {
        Collection<EntityWrap<Spaceship>> cache = (Collection<EntityWrap<Spaceship>>) context.retrieveBaseEntityList(Spaceship.class);
        if (cache.contains(new EntityWrap<>(spaceship)))
            throw new EntityExistsException(spaceship);

        context.updateCache(spaceship.getClass());
        if (cache.contains(new EntityWrap<>(spaceship)))
            throw new EntityExistsException(spaceship);

        EntityRepositoryImpl.getInstance().create(spaceship);
        return spaceship;
    }

    @Override
    public void deleteSpaceship(Spaceship spaceship) throws EntityNotFoundException, IOException {
        Collection<EntityWrap<Spaceship>> cache = (Collection<EntityWrap<Spaceship>>) context.retrieveBaseEntityList(Spaceship.class);
        cache.stream().filter(e -> e.getEntity().equals(spaceship)).forEach(wrap -> wrap.setValid(false));

        EntityRepositoryImpl.getInstance().delete(spaceship);
    }
}
