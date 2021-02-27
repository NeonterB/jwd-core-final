package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.epam.jwd.core_final.util.ReadStrategy;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;

public class CrewReader implements ReadStrategy {
    @Override
    public Collection<CrewMember> readEntities(File input) {
        Collection<CrewMember> crewMembers = new HashSet<>();
        CrewMemberFactory factory = new CrewMemberFactory();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
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
            //todo
        }
        return crewMembers;
    }

    public static void main(String[] args) {
        PropertyReaderUtil.loadProperties();
        CrewReader reader = new CrewReader();
        System.out.println(reader.readEntities(new File(ApplicationProperties.getInstance().getFullCrewMemberDir())));

    }
}
