package com.epam.jwd.core_final.io.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.io.EraseStrategy;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class CrewEraser implements EraseStrategy<CrewMember> {
    @Override
    public void erase(CrewMember entity) throws EntityNotFoundException, IOException {
        Queue<String> hashLines = new LinkedList<>();
        String line = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                        ApplicationProperties.getInstance().getCrewFileDir()
                ))
        ))) {
            StringBuilder memberToDelete = new StringBuilder();
            memberToDelete.append(entity.getRole().getId()).append(',')
                    .append(entity.getName()).append(',')
                    .append(entity.getRank().getId()).append(';');
            line = reader.readLine();
            while (line.charAt(0) == '#') {
                hashLines.add(line);
                line = reader.readLine();
            }
            int index = line.indexOf(memberToDelete.toString());
            if (index == -1) throw new EntityNotFoundException(entity.toString() + " not found");
            line = StringUtils.replaceIgnoreCase(line, memberToDelete.toString(), "");

        }

        try (FileWriter writer = new FileWriter(new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource(
                        ApplicationProperties.getInstance().getCrewFileDir()
                )).getPath()
        ), false)) {
            while(!hashLines.isEmpty()){
                writer.write(hashLines.poll());
                writer.write("\n");
            }
            writer.write(line);
        }
    }
}
