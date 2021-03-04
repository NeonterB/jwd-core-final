package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    Collection<CrewMember> findAllCrewMembers();

    Collection<CrewMember> findAllCrewMembersByCriteria(Criteria<CrewMember> criteria) throws EntityNotFoundException;

    Optional<CrewMember> findCrewMemberByCriteria(Criteria<CrewMember> criteria) throws EntityNotFoundException;

    CrewMember createCrewMember(CrewMember crewMember) throws EntityExistsException, IOException;

    void deleteCrewMember(CrewMember crewMember) throws EntityNotFoundException, IOException;
}