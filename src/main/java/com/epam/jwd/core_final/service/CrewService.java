package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.EntityCollisionException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    Collection<CrewMember> findAllCrewMembers();

    Collection<CrewMember> findAllCrewMembersByCriteria(Criteria<CrewMember> criteria);

    Optional<CrewMember> findCrewMemberByCriteria(Criteria<CrewMember> criteria);

    CrewMember createCrewMember(CrewMember crewMember) throws EntityCollisionException;
}