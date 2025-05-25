package com.javafullstacklibrary.frontend;

import java.io.File;
import java.util.logging.*;

public class LoggerUtil {
    public static Logger getFileLogger(Class<?> clazz, String logFileName) {
        Logger logger = Logger.getLogger(clazz.getName());
        try {
            // Delete old log file if present
            File logFile = new File(logFileName);
            if (logFile.exists()) {
                logFile.delete();
            }
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