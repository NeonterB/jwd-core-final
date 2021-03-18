package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.EntityWrap;
import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.repository.impl.EntityRepositoryImpl;
import com.epam.jwd.core_final.service.CrewService;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class CrewServiceImpl implements CrewService {
    private static final CrewService instance = CrewServiceProxy.newInstance(new CrewServiceImpl(
            Main.getApplicationMenu().getApplicationContext()
    ));

    public ApplicationContext getContext() {
        return context;
    }

    private final ApplicationContext context;

    private CrewServiceImpl(ApplicationContext context) {
        this.context = context;
    }

    public static CrewService getInstance() {
        return instance;
    }

    @Override
    public Collection<CrewMember> findAllCrewMembers() {
        context.updateCache(CrewMember.class);
        return ((Collection<EntityWrap<CrewMember>>) context.retrieveBaseEntityList(CrewMember.class)).stream()
                .filter(EntityWrap::isValid)
                .map(EntityWrap::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CrewMember> findAllCrewMembersByCriteria(Criteria<CrewMember> criteria) {
        Collection<EntityWrap<CrewMember>> cache = (Collection<EntityWrap<CrewMember>>) context.retrieveBaseEntityList(CrewMember.class);
        Collection<CrewMember> foundMembers = cache.stream()
                .filter((wrap) -> wrap.isValid() && criteria.meetsCriteria(wrap.getEntity()))
                .map(EntityWrap::getEntity)
                .collect(Collectors.toList());

        //if nothing found - update cache
        if (foundMembers.isEmpty()) {
            context.updateCache(CrewMember.class);
            foundMembers = cache.stream()
                    .filter((wrap) -> wrap.isValid() && criteria.meetsCriteria(wrap.getEntity()))
                    .map(EntityWrap::getEntity)
                    .collect(Collectors.toList());
        }

        return foundMembers;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<CrewMember> criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findAny();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws EntityExistsException, IOException {
        Collection<EntityWrap<CrewMember>> cache = (Collection<EntityWrap<CrewMember>>) context.retrieveBaseEntityList(CrewMember.class);
        if (cache.contains(new EntityWrap<>(crewMember)))
            throw new EntityExistsException(crewMember);

        context.updateCache(crewMember.getClass());
        if (cache.contains(new EntityWrap<>(crewMember)))
            throw new EntityExistsException(crewMember);

        EntityRepositoryImpl.getInstance().create(crewMember);
        return crewMember;
    }

    @Override
    public void deleteCrewMember(CrewMember crewMember) throws EntityNotFoundException, IOException {
        Collection<EntityWrap<CrewMember>> cache = (Collection<EntityWrap<CrewMember>>) context.retrieveBaseEntityList(CrewMember.class);
        cache.stream().filter(e -> e.getEntity().equals(crewMember)).forEach(wrap -> wrap.setValid(false));

        EntityRepositoryImpl.getInstance().delete(crewMember);
    }
}
