package utilities.helper;

import com.hansencx.solutions.logger.Log;
import com.hansencx.solutions.reporting.extentreports.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;
import utilities.configuration.InitialData;
import utilities.helper.screenshot.ScreenCaptor;

import java.io.IOException;
import java.util.*;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

public class SoftAssert extends Assertion {
    private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
    private static List<String> screenshotNameList = new ArrayList<>();
    private static StringBuilder noScreenshotErrorMsg = new StringBuilder();
    private static Map<String, StringBuilder> failMsgMap = new HashMap<>();
    private WebDriver driver;
    private String methodName;
    private static int n = 0;
    private Boolean msgAvailableFlag = true;

    public SoftAssert(WebDriver driver, String name) {
        this.driver = driver;
        this.methodName = name;
    }
    public SoftAssert(){

    }
    public List<String> getScreenshotNameList() {
        return screenshotNameList;
    }

    public Map<String, StringBuilder> getErrorMessage() {
        return failMsgMap;
    }

    public StringBuilder getNoScreenshotErrorMessage() {
        return noScreenshotErrorMsg;
    }

    protected void doAssert(IAssert<?> var1) {
        this.onBeforeAssert(var1);
        try {
            var1.doAssert();
            this.onAssertSuccess(var1);
        } catch (AssertionError errorContent) {
            StringBuilder failMsg = new StringBuilder("The failed assertion: ");
            failMsg.append(errorContent.getMessage());
            failMsg.append(" ");
            if(errorContent.getCause() != null){
                failMsg.append(errorContent.getCause());
            }
            failMsg.append("#");

            try{
                msgAvailableFlag = var1.getMessage().isEmpty();
            }catch (NullPointerException e){
                Log.info("There is no message when assertion");
            }
            if(!msgAvailableFlag){
                if(!var1.getMessage().contains("SQL")) {
                    String screenshotName = captureScreenshot();
                    failMsgMap.put(screenshotName,failMsg);
                }else{
                    noScreenshotErrorMsg.append(failMsg);
                }
            }else{
                failMsgMap.put(captureScreenshot(),failMsg);
            }

            //end of print msg
            this.onAssertFailure(var1, errorContent);
            this.m_errors.put(errorContent, var1);
        } finally {
            this.onAfterAssert(var1);
        }

    }

    private String captureScreenshot(){
        n++;
        String screenshotName =  methodName+"_" + InitialData.TIMESTAMP +"_"+ n;
        screenshotNameList.add(screenshotName);
        String screenshotDirectory = separatorsToSystem(ExtentManager.getReportDirectory() + "\\FailedTestsScreenshots\\");
        FileHelper.createDirectory(screenshotDirectory);
        try {
            ScreenCaptor.takeFullScreenshot(driver, screenshotName, screenshotDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotName;
    }

    public void assertAll(){
        if (!this.m_errors.isEmpty()) {
            StringBuilder var1 = new StringBuilder("The following asserts failed:");
            boolean var2 = true;
            Iterator var3 = this.m_errors.keySet().iterator();

            while(var3.hasNext()) {
                AssertionError var4 = (AssertionError)var3.next();
                if (var2) {
                    var2 = false;
                } else {
                    var1.append(",\r\n");
                }
                var1.append("\n\t");
                var1.append(var4.getMessage());
                for(Throwable var5 = var4.getCause(); var5 != null; var5 = var5.getCause()) {
                    var1.append(" ").append(var5.getMessage());
                }
            }
            throw new AssertionError(var1.toString());
        }
    }
}
