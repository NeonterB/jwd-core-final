package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.Collection;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class SpacemapServiceImpl implements SpacemapService {
    private static final SpacemapServiceImpl instance = new SpacemapServiceImpl(
            Main.getApplicationMenu().getApplicationContext()
    );
    private final ApplicationContext context;

    private SpacemapServiceImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ApplicationContext getContext() {
        return context;
    }

    public static SpacemapServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Collection<Planet> findAll() {
        return (Collection<Planet>) context.retrieveBaseEntityList(Planet.class);
    }

    @Override
    public Optional<Planet> findPlanetById(Long id) {
        return ((Collection<Planet>) context.retrieveBaseEntityList(Planet.class))
                .stream()
                .filter(planet -> planet.getId().equals(id))
                .findFirst();
    }

    @Override
    public Planet getRandomPlanet() throws EntityNotFoundException {
        Collection<Planet> cache = (Collection<Planet>) context.retrieveBaseEntityList(Planet.class);
        if (cache.isEmpty()) context.updateCache(Planet.class);
        return cache.stream()
                .skip((int) (cache.size() * Math.random()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Spacemap is empty"));
    }
}
