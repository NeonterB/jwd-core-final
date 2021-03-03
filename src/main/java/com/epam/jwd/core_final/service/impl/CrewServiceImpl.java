package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.EntityCollisionException;
import com.epam.jwd.core_final.repository.impl.EntityRepositoryImpl;
import com.epam.jwd.core_final.service.CrewService;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {

    @Override
    public Collection<CrewMember> findAllCrewMembers() {
        return Main.getApplicationMenu().getApplicationContext().updateCache(CrewMember.class);
    }

    @Override
    public Collection<CrewMember> findAllCrewMembersByCriteria(Criteria<CrewMember> criteria) {
        Collection<CrewMember> cache =  Main.getApplicationMenu()
                                            .getApplicationContext()
                                            .retrieveBaseEntityList(CrewMember.class);
        Collection<CrewMember> foundMembers = cache.stream().filter(criteria::meetsCriteria).collect(Collectors.toList());

        //if nothing found - update cache
        if (foundMembers.isEmpty()) {
            cache = Main.getApplicationMenu().getApplicationContext().updateCache(CrewMember.class);
        }
        return cache.stream().filter(criteria::meetsCriteria).collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<CrewMember> criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findAny();
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws EntityCollisionException {
        try {
            EntityRepositoryImpl.getInstance().create(crewMember);
        } catch (IOException e) {
            //todo
        }
        return crewMember;
    }
}
