package com.hansencx.solutions.core;

import com.aventstack.extentreports.ExtentReports;
import com.hansencx.solutions.logger.Log;
import com.hansencx.solutions.reporting.extentreports.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utilities.configuration.DriverConfiguration;
import utilities.configuration.driver.DriverType;
import utilities.helper.Browser;

import java.net.MalformedURLException;

/**
 * BaseTest class
 *
 * @author  Vi Nguyen, Huong Trinh
 * @version 1.0
 * @since   2018-12-03
 * @Updated Nhi Dinh 17/01/2019
 *
 */
public class BaseTest {
    private WebDriver driver;
    private static ExtentReports extent;

    /**
     * Constructor
     */
    public WebDriver getDriver(){
        return driver;
    }

    @BeforeSuite(description = "Setting Report Before Suite")
    public void settingReportBeforeSuite(ITestContext iTestContext){
        Log.startLog();
        extent = ExtentManager.getInstance();
        String suiteName = iTestContext.getCurrentXmlTest().getSuite().getName();
        ExtentManager.createRootNode(extent, suiteName);
    }

    @Parameters({"browser","mode"})
    @BeforeMethod (description = "Set Up Browser")
    public void setUp(final DriverType browser, final String mode, ITestContext testContext){
        if(mode.equals("NonRemote")){
            Browser.setup(browser, testContext);
            driver = (WebDriver) testContext.getAttribute("driver");
        }else if(mode.equals("Remote")){
            try {
                driver = DriverConfiguration.configureRemoteDriver(browser.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        Browser.maximize();
    }

    @BeforeTest (description = "Set Up Logger Before Test")
    public void setUpLoggerBeforeTest(ITestContext testContext) {
        String testCaseName = testContext.getName();
        Log.startLog();
        Log.startTestCase(testCaseName);
    }

    /**
     * quit driver after running a test suite
     * @author Huong Trinh
     * @param
     * @return Nothing.
     * @since 2018-12-03
     * @see ITestContext
     */
    @AfterMethod (description = "Clean Session")
    public synchronized void clean(ITestContext testContext){
        extent.flush();
        String testCaseName = testContext.getName();
        Log.endTestCase(testCaseName);
        Log.info("Closing browser after test");
        Browser.quit();
    }
    /**
     * @author Huong Trinh
     * @since 1/18/2019
     * @Update: update action related to Jira
     */
    @AfterSuite (description = "Ending Log After Suite")
    public void endingLogAfterSuite() {
        Log.info("JIRA ISSUE CREATION HANDLING");
//        String keyIssue = JiraHelper.createJiraIssue(TestListener.failIDList);
//        JiraHelper.importReportAttachment(keyIssue);
        Log.info("ENDING SUITE");
        Log.endLog();
    }
}