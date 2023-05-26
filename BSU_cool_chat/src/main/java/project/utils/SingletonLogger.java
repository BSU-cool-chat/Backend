package project.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SingletonLogger {
    private static Logger logger;

    public static Logger getInstance() {
        if (logger == null) {
            logger = Logger.getLogger(SingletonLogger.class.getName());
            try {
                FileHandler handler = new FileHandler("log.txt");
                SimpleFormatter formatter = new SimpleFormatter();
                handler.setFormatter(formatter);

                logger.addHandler(handler);
                logger.setUseParentHandlers(false);
                logger.setLevel(Level.ALL);
                logger.info("Logger setup finished successfully");
            } catch (IOException e) {
                logger.severe("Unable log to file");
                throw new RuntimeException(e);
            }
        }
        return logger;
    }

}
