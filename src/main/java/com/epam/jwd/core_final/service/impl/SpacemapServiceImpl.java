package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.Collection;

public class SpacemapServiceImpl implements SpacemapService {
    @Override
    public Planet getRandomPlanet() {
        ApplicationContext context = Main.getApplicationMenu().getApplicationContext();
        Collection<Planet> cache = context.retrieveBaseEntityList(Planet.class);
        if (cache.isEmpty()) cache = context.updateCache(Planet.class);
        return cache.stream()
                .skip((int) (cache.size() * Math.random()))
                .findFirst().get();
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        Planet.Point firstPoint = first.getLocation();
        Planet.Point secondPoint = second.getLocation();
        return (int) Math.sqrt(
                (firstPoint.getX() - secondPoint.getX()) * (firstPoint.getX() - secondPoint.getX()) +
                (firstPoint.getY() - secondPoint.getY()) * (firstPoint.getY() - secondPoint.getY())
        );
    }
}
