package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
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

    Collection<Spaceship> findAllSpaceships();

    Collection<Spaceship> findAllSpaceshipsByCriteria(Criteria<Spaceship> criteria);

    Optional<Spaceship> findSpaceshipByCriteria(Criteria<Spaceship> criteria);

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // spaceship unique criteria - only name!
    Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException, EntityExistsException;

    public void deleteSpaceship(Spaceship spaceship) throws EntityNotFoundException, IOException;
}
