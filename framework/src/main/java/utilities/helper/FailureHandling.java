package utilities.helper;

import com.hansencx.solutions.logger.Log;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */


public class FailureHandling {
    public static void continueAtFailedTestCase(AssertionError e, String testcaseName){
        Log.warning("Failed at case: " + testcaseName);
        Log.warning(e.getMessage());
        System.out.println("Failed at case: " + testcaseName);
        System.out.println(e.getMessage());
    }
}
