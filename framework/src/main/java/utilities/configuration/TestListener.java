package utilities.configuration;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.hansencx.solutions.logger.Log;
import com.hansencx.solutions.reporting.extentreports.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.helper.FileHelper;
import utilities.helper.SoftAssert;
import utilities.helper.screenshot.ScreenCaptor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = ExtentManager.getTest();
    private static String methodDes;
    private static String message;
    private static WebDriver driver;
    public static List<String> failIDList = new ArrayList<>();

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        message = "TEST COMPLETED - REPORT IS UPDATED";
        Log.info(message);
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        String methodName = result.getMethod().getMethodName();
        if (description != null) {
            methodDes = description;
        } else {
            methodDes = methodName;
        }
        message = setMessage("START", methodDes);
        ExtentManager.createTest(methodDes);
        Log.info(message);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        message = setMessage("PASS", methodDes);
        test.get().pass(message);
        Log.info(message);

        // Vi: this is to print out the path of the difference when comparing xml
        if ("" != InitialData.FINISH_TEST_INFO)
            test.get().info(InitialData.FINISH_TEST_INFO);
    }

    /**
     * @author Huong Trinh
     * @update: update code to get fail ID test case for Jira
     * @since 1/22/2019
     */
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        MediaEntityModelProvider mediaModel = null;
        message = setMessage("FAILED", methodDes);
        test.get().fail(message);
        String location = separatorsToSystem(ExtentManager.getReportDirectory() + "\\FailedTestsScreenshots\\");
        SoftAssert softAssert = new SoftAssert();

        StringBuilder noScreenshotErrorMsg = new StringBuilder(softAssert.getNoScreenshotErrorMessage());
        List<String>  screenshotNameList = softAssert.getScreenshotNameList();
        if(noScreenshotErrorMsg.length() != 0) {
            String[] singleMsg = noScreenshotErrorMsg.toString().split("#");
            for(String str : singleMsg) {
                test.get().fail(str);
            }
        }

        if(!screenshotNameList.isEmpty()){
            for(String name: screenshotNameList){
                String destination = location + name +".png";
                String encodedScreenshot = ScreenCaptor.encodedFile(new File(destination));
                try {
                    mediaModel = MediaEntityBuilder.createScreenCaptureFromBase64String(encodedScreenshot).build();
                } catch (IOException e) {
                    Log.error(e.getMessage());
                }
                test.get().fail(softAssert.getErrorMessage().get(name).toString().replace("#",""), mediaModel);
            }
        }

        //Huong: currently list out failed method name in JIRA Issue, can be changed
        failIDList.add(result.getMethod().getMethodName());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        message = setMessage("SKIPPED", methodDes);
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Log.info("onTestFailedButWithinSuccessPercentage for " + methodDes);
    }

    private String setMessage(String status, String description) {
        return "TEST " + status + ": " + description;
    }
}