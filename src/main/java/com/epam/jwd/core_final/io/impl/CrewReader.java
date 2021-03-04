package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.io.ReadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CrewReader implements ReadStrategy<CrewMember> {
    @Override
    public Collection<CrewMember> readEntities() throws IOException {
        Collection<CrewMember> crewMembers = new LinkedHashSet<>();
        CrewMemberFactory factory = new CrewMemberFactory();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        ApplicationProperties.getInstance().getCrewFileDir()
                ))
        ))) {
            String line = reader.readLine();
            while (line.charAt(0) == '#') {
                line = reader.readLine();
            }
            String[] members = line.split(";");
            for (String member : members) {
                String[] args = member.split(",");
                crewMembers.add(factory.create(
                        Role.resolveRoleById(Integer.parseInt(args[0])),
                        args[1],
                        Rank.resolveRankById(Integer.parseInt(args[2]))
                ));
            }
        }
        return crewMembers;
    }
}
