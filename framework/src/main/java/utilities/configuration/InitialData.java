package utilities.configuration;

import org.openqa.selenium.Platform;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class InitialData {
    public static String ROOT_DIRECTORY = System.getProperty("user.dir");

    public static String PARENT_DIR = getProjectDirectory();
    public static Platform PLATFORM = getCurrentPlatform();
    public static String PLATFORM_NAME;
    public static String TIMESTAMP = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

    //LOG DIRECTORY AND FILE PATH
    private static String FILE_NAME_LOG = TIMESTAMP + ".log";
    public static String LOG_DIR_PATH = PARENT_DIR+"\\solutions\\regression-tests\\logs";
    public static String LOG_FILE_PATH = LOG_DIR_PATH + "/" + FILE_NAME_LOG;
    public static String LOG_CONFIG_FILE_PATH = "./framework/src/main/resources/log4j.xml";

    //REPORT DIRECTORY PATH
    public static String FILE_NAME_REPORT = "REPORT-"+TIMESTAMP+".html";
    public static String REPORT_DIR_PATH = PARENT_DIR+ "\\solutions\\regression-tests\\reports" + "\\Report-"+TIMESTAMP;
    public static String REPORT_FILE_PATH = REPORT_DIR_PATH +"/"+FILE_NAME_REPORT;

    //FILE PATH OF extent-config.xml file
    public static String REPORT_CONFIG_XML_FILE_PATH = PARENT_DIR+"\\solutions\\framework\\src\\main\\java\\com\\hansencx\\solutions\\reporting\\extentreports\\config\\extent-config.xml";

    //Get current platform
    private static Platform getCurrentPlatform () {
        if (PLATFORM == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                PLATFORM_NAME = "WINDOWS";
                PLATFORM = Platform.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                PLATFORM_NAME = "LINUX";
                PLATFORM = Platform.LINUX;
            } else if (operSys.contains("mac")) {
                PLATFORM_NAME = "MAC";
                PLATFORM = Platform.MAC;
            }
        }
        return PLATFORM;
    }

    //Get Project Directory:
    public static String getProjectDirectory() {
        Path dirPath = Paths.get(ROOT_DIRECTORY).toAbsolutePath();
        PARENT_DIR = dirPath.getParent().toString();
        return PARENT_DIR;
    }
}
