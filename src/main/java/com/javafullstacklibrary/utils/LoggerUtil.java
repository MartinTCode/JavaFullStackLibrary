package com.javafullstacklibrary.utils;

import java.util.logging.*;

public class LoggerUtil {
    public static Logger getFileLogger(Class<?> clazz, String logFileName) {
        Logger logger = Logger.getLogger(clazz.getName());
        try {
            // Prevent duplicate handlers
            if (logger.getHandlers().length == 0) {
                FileHandler fh = new FileHandler(logFileName, true);
                fh.setFormatter(new SimpleFormatter());
                fh.setLevel(Level.ALL);
                logger.addHandler(fh);
                logger.setUseParentHandlers(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logger;
    }
}