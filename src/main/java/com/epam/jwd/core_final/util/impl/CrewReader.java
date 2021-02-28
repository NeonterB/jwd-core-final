package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.CrewMemberFactory;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.util.ReadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;

public class CrewReader implements ReadStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CrewReader.class);

    @Override
    public Collection<CrewMember> readEntities(InputStream input) throws IOException {
        Collection<CrewMember> crewMembers = new HashSet<>();
        CrewMemberFactory factory = new CrewMemberFactory();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
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
        } catch (IOException e) {
            throw e;
        }
        return crewMembers;
    }
}
