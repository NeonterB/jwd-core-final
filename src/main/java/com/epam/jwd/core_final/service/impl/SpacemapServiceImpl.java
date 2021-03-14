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
    private static final SpacemapServiceImpl instance = new SpacemapServiceImpl();

    private SpacemapServiceImpl(){}

    public static SpacemapServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Collection<Planet> findAll() {
        return (Collection<Planet>) Main.getApplicationMenu().getApplicationContext().retrieveBaseEntityList(Planet.class);
    }

    @Override
    public Optional<Planet> findPlanetById(Long id) {
        return ((Collection<Planet>) Main.getApplicationMenu().getApplicationContext().retrieveBaseEntityList(Planet.class))
                .stream()
                .filter(planet -> planet.getId().equals(id))
                .findFirst();
    }

    @Override
    public Planet getRandomPlanet() throws EntityNotFoundException {
        ApplicationContext context = Main.getApplicationMenu().getApplicationContext();
        Collection<Planet> cache = (Collection<Planet>) context.retrieveBaseEntityList(Planet.class);
        if (cache.isEmpty()) context.updateCache(Planet.class);
        return cache.stream()
                .skip((int) (cache.size() * Math.random()))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Spacemap is empty"));
    }
}
