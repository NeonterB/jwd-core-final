package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.MethodInfo;
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
    @MethodInfo(menuPosition = 1, description = "Find all crew members")
    Collection<CrewMember> findAllCrewMembers();

    @MethodInfo(menuPosition = 2, description = "Find all crew members by criteria")
    Collection<CrewMember> findAllCrewMembersByCriteria(Criteria<CrewMember> criteria);

    @MethodInfo(menuPosition = 3, description = "Find crew member by criteria")
    Optional<CrewMember> findCrewMemberByCriteria(Criteria<CrewMember> criteria);

    @MethodInfo(menuPosition = 4, description = "Create new crew member")
    CrewMember createCrewMember(CrewMember crewMember) throws EntityExistsException, IOException;

    @MethodInfo(menuPosition = 5, description = "Delete crew member")
    void deleteCrewMember(CrewMember crewMember) throws EntityNotFoundException, IOException;
}