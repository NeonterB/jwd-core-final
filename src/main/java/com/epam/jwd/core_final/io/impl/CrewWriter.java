package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.io.WriteStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CrewWriter implements WriteStrategy<CrewMember> {
    @Override
    public void writeEntity(CrewMember crewMember) throws IOException {
        try (FileWriter writer = new FileWriter(new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource(
                        ApplicationProperties.getInstance().getCrewFileDir()
                )).getPath()
        ), true)) {
            writer.write(
                    crewMember.getRole().getId() + "," +
                            crewMember.getName() + "," +
                            crewMember.getRank().getId() + ";"
            );
        }
    }
}
