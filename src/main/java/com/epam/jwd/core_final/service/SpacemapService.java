package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.EntityNotFoundException;

public interface SpacemapService {

    Planet getRandomPlanet() throws EntityNotFoundException;

    static long getDistanceBetweenPlanets(Planet first, Planet second){
        Planet.Point firstPoint = first.getLocation();
        Planet.Point secondPoint = second.getLocation();
        return (long) Math.sqrt(
                (firstPoint.getX() - secondPoint.getX()) * (firstPoint.getX() - secondPoint.getX()) +
                        (firstPoint.getY() - secondPoint.getY()) * (firstPoint.getY() - secondPoint.getY())
        );
    };
}
