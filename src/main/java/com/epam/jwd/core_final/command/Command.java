package com.epam.jwd.core_final.command;

import com.epam.jwd.core_final.exception.EntityExistsException;
import com.epam.jwd.core_final.exception.EntityNotFoundException;
import com.epam.jwd.core_final.exception.EntityOccupiedException;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Command {
    void execute() throws InvocationTargetException, IllegalAccessException, EntityNotFoundException, IOException, EntityExistsException, EntityOccupiedException, InvalidStateException;
}
