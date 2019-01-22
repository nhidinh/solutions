package com.hansencx.solutions.reporting.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.configuration.InitialData;
import utilities.helper.FileHelper;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 * ExtentManager class (IN DEVELOPING...)
 *
 * @author  Vi Nguyen
 * @version 1.0
 * @since
 */
public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    private static ExtentTest parent;
    private static ExtentTest child;

    private static String reportDir_path;

    /**
     * Get instance of ExtentHtmlReporter
     * @author Huong Trinh
     * @param
     * @return ExtentReports object
     * @since   03.01.2019
     * @see ExtentReports
     */
    public final static ExtentReports getInstance( ) {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    /**
     * Create instance of ExtentHtmlReporter, load extent-config.xml file
     * @author Huong Trinh
     * @return ExtentReport object
     * @since   03.01.2019
     */
    private static ExtentReports createInstance() {
        String fileName = setReportFileName();
        FileHelper.createDirectory(reportDir_path);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.loadXMLConfig(new File(InitialData.REPORT_CONFIG_XML_FILE_PATH));

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }

    /**
     * Create Node to listen for report.
     * @author Huong Trinh
     * @param displayedName is used to get name of class/testcontext(upper level)
     * @return void
     * @since   03.01.2019
     */

    public static void createRootNode(ExtentReports extent,String displayedName){
        parent = extent.createTest(displayedName);
        parentTest.set(parent);
    }

    /**
     * Create Test to listen for report data from methods.
     * @author Huong Trinh
     * @param  testDescription is used to get name of method
     * @return void
     * @since   03.01.2019
     */

    public static void createTest(String testDescription){
        System.out.println(" create test " + parentTest.get());
        child = parentTest.get().createNode(testDescription);
        test.set(child);
    }

    /**
     * Get Extent Report Test
     * @author Nhi Dinh
     * @return ExtentTest
     * @since   17.01.2019
     */

    public static ThreadLocal<ExtentTest> getTest(){
        return test;
    }

    /**
     * Get Report Directory Path
     * @author Nhi Dinh
     * @return String
     * @since   18.01.2019
     */

    public static String getReportDirectory(){
        return reportDir_path;
    }

    /**
     * setReportFileName
     * @author Nhi Dinh
     * @return String
     * @since   17.01.2019
     */

    private static String setReportFileName() {
        reportDir_path = separatorsToSystem(InitialData.REPORT_DIR_PATH);
        String reportFile_path = separatorsToSystem(InitialData.REPORT_FILE_PATH);
        return reportFile_path;
    }
}
