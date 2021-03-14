package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.MethodInfo;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface SpacemapService {

    @MethodInfo(menuPosition = 1, description = "Find all planets")
    Collection<Planet> findAll();

    @MethodInfo(menuPosition = 2, description = "Get random planet")
    Planet getRandomPlanet() throws EntityNotFoundException;

    Optional<Planet> findPlanetById(Long id);

    @MethodInfo(menuPosition = 3, description = "Get distance between planets")
    default long getDistanceBetweenPlanets(Planet first, Planet second){
        Planet.Point firstPoint = first.getLocation();
        Planet.Point secondPoint = second.getLocation();
        return (long) Math.sqrt(
                (firstPoint.getX() - secondPoint.getX()) * (firstPoint.getX() - secondPoint.getX()) +
                        (firstPoint.getY() - secondPoint.getY()) * (firstPoint.getY() - secondPoint.getY())
        );
    };
}
