package utilities.helper;

import com.hansencx.solutions.logger.Log;
import org.openqa.selenium.WebElement;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 2/14/2019
 */


public class ActionLogger extends Log {
    //SET LOG MESSAGE: START ACTION
    private static String actionObject="";
    private static String dataTextValue="";
    private static String elementLocator="";

    private static String startMessage;
    private static String endMessage_PASSED;
    private static String endMessage_FAILED;

    public static String getStartMessage() {
        return startMessage;
    }

    public static String getEndMessage_PASSED() {
        return endMessage_PASSED;
    }

    public static String getEndMessage_FAILED() {
        return endMessage_FAILED;
    }

    private static void checkValue(WebElement element, String text){
        if (element != null) {
            elementLocator = getLocatorOfElement(element);
            actionObject = " element with locator ";
        }else {
            elementLocator = "";
            actionObject = "";
        }

        if (!text.equals("")){
            dataTextValue = " [" + text+"]";
        }else{
            dataTextValue="";
        }
    }
    private static String getLocatorOfElement(WebElement element) {
        String elementName = element.toString();
        int index = elementName.indexOf("> ") + 2;
        return "[" + elementName.substring(index);
    }

    public static void setActionLoggerMessages(String action, String textValue, WebElement element){
        checkValue(element, textValue);
        String messageBody = " action " + action  + dataTextValue + actionObject + elementLocator;
        startMessage = "Start" + messageBody;
        endMessage_FAILED = "FAILED - End" + messageBody;
        endMessage_PASSED = "SUCCESS - End" + messageBody;
    }

}
