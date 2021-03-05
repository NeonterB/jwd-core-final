package com.epam.jwd.core_final.context;

import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default Object printAvailableOptions() {
        return null;
    }

    default Object handleUserInput(Object o) {
        return null;
    }
}
