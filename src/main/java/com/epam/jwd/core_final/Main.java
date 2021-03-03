package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static ApplicationMenu applicationMenu;

    public static void main(String[] args) {
        try {
            applicationMenu = Application.start();
        } catch (InvalidStateException e) {
            logger.error(e.getMessage());
        }
    }

    public static ApplicationMenu getApplicationMenu() {
        return applicationMenu;
    }
}