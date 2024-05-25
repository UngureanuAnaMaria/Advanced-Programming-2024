package com.nictas.eclipselink.postgresql;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfiguration {
    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            try {
                logger = Logger.getLogger("MyLogger");
                logger.setUseParentHandlers(false); //disable default console handler

                //console handler
                var consoleHandler = new ConsoleHandler();
                consoleHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(consoleHandler);

                //file handler
                var fileHandler = new FileHandler("application.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
}
