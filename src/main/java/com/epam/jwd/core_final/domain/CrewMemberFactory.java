package com.epam.jwd.core_final.domain;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    @Override
    public CrewMember create(Object... args) {
        if (args.length != 3) throw new IllegalArgumentException("CrewMember creation requires 3 arguments");
        try{
            return new CrewMember((Role) args[0], (String) args[1], (Rank) args[2]);
        }catch (ClassCastException e){
            throw e;
        }
    }
}
