package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityCollisionException;
import com.epam.jwd.core_final.repository.impl.EntityRepositoryImpl;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {
    @Override
    public Collection<Spaceship> findAllSpaceships() {
        return Main.getApplicationMenu().getApplicationContext().updateCache(Spaceship.class);
    }

    @Override
    public Collection<Spaceship> findAllSpaceshipsByCriteria(Criteria<Spaceship> criteria) {
        Collection<Spaceship> cache =  Main.getApplicationMenu()
                .getApplicationContext()
                .retrieveBaseEntityList(Spaceship.class);
        Collection<Spaceship> foundSpaceships = cache.stream().filter(criteria::meetsCriteria).collect(Collectors.toList());

        //if nothing found - update cache
        if (foundSpaceships.isEmpty()) {
            cache = Main.getApplicationMenu().getApplicationContext().updateCache(Spaceship.class);
        }
        return cache.stream().filter(criteria::meetsCriteria).collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<Spaceship> criteria) {
        return findAllSpaceshipsByCriteria(criteria).stream().findAny();
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws EntityCollisionException {
        try {
            EntityRepositoryImpl.getInstance().create(spaceship);
        } catch (IOException e) {
            //todo
        }
        return spaceship;
    }
}
