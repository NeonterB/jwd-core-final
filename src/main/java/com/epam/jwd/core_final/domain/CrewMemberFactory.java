package com.epam.jwd.core_final.domain;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    /**
     * create new {@link CrewMember}
     *
     * @param args {@link Role} - role, {@link String} - name, {@link Rank} - rank
     * @return brand new {@link CrewMember}
     */
    @Override
    public CrewMember create(Object... args) throws IllegalArgumentException, ClassCastException {
        if (args.length != 3) throw new IllegalArgumentException("CrewMember creation requires 3 arguments");
        return new CrewMember((Role) args[0], (String) args[1], (Rank) args[2]);
    }

    public CrewMember create(String line) throws IllegalArgumentException, ClassCastException {
        String[] args = line.split(",");
        if (args.length != 3) throw new IllegalArgumentException("CrewMember creation requires 3 arguments");
        return create(
                Role.resolveRoleById(Long.parseLong(args[0])),
                args[1],
                Rank.resolveRankById(Long.parseLong(args[2]))
        );
    }
}
