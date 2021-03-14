package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Command;
import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static ApplicationMenu applicationMenu;

    public static void main(String... args) {
        try {
            applicationMenu = Application.start();


            for (Command command = applicationMenu.handleUserInput();
                 command != null;
                 command = applicationMenu.handleUserInput()) {
                try {
                    command.execute();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    System.out.println(e.getMessage());
                    break;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        } catch (InvalidStateException | IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static ApplicationMenu getApplicationMenu() {
        return applicationMenu;
    }
}