package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.MethodInfo;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface SpaceshipService {

    @MethodInfo(menuPosition = 1, description = "Find all spaceships")
    Collection<Spaceship> findAllSpaceships();

    @MethodInfo(menuPosition = 2, description = "Find all spaceships by criteria")
    Collection<Spaceship> findAllSpaceshipsByCriteria(Criteria<Spaceship> criteria);

    @MethodInfo(menuPosition = 3, description = "Find spaceship by criteria")
    Optional<Spaceship> findSpaceshipByCriteria(Criteria<Spaceship> criteria);

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // spaceship unique criteria - only name!
    @MethodInfo(menuPosition = 4, description = "Create new spaceship")
    Spaceship createSpaceship(Spaceship spaceship) throws EntityExistsException, IOException;

    @MethodInfo(menuPosition = 5, description = "Delete spaceship")
    void deleteSpaceship(Spaceship spaceship) throws EntityNotFoundException, IOException;
}
