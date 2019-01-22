package com.hansencx.solutions.logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import utilities.helper.FileHelper;
import utilities.configuration.InitialData;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 * Log class (IN DEVELOPING...)
 *
 * @author  Nhi Dinh
 * @version 2.0
 * @since
 * @Update 17/01/2019
 */
public class Log {
    private static Logger log;
    private static String configFile_path;

    public static void initLogger() {
        System.out.println("Setting Log File Location...");
        setLogFileLocation();
        getLog4jConfigurationFile();
        log = Logger.getLogger(Log.class.getName());
    }

    private static void setLogFileLocation() {
        String logDir_path = separatorsToSystem(InitialData.LOG_DIR_PATH);
        String logFile_path = separatorsToSystem(InitialData.LOG_FILE_PATH);
        System.out.println("Log File Path is: " + logFile_path);
        FileHelper.createDirectory(logDir_path);
        System.setProperty("logLocation", logFile_path);
    }

    private static void getLog4jConfigurationFile() {
        configFile_path = separatorsToSystem(InitialData.LOG_CONFIG_FILE_PATH);
        DOMConfigurator.configure(configFile_path);
    }

    public static void startLog() {
        initLogger();
    }

    public static void endLog() {
        log.info("End Log...");
    }

    public static void startTestCase(String sTestCaseName) {
        log.info("********************* START TESTCASE: " + sTestCaseName + "*********************");
    }

    public static void endTestCase(String sTestCaseName) {
        log.info("********************* END OF TEST CASE: " + sTestCaseName + "*********************");
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void warning(String message) {
        log.warn(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void fatal(String message) {
        log.fatal(message);
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void trace(String message) {
        log.trace(message);
    }
}
