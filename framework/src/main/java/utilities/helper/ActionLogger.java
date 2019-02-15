package utilities.helper;

import com.hansencx.solutions.logger.Log;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 2/14/2019
 */


public class ActionLogger extends Log {
    //SET LOG MESSAGE: START ACTION
    public static String setStartMessage(String action, String textValue, String elementLocator, String tail){
        if (!textValue.equals("")){
            textValue = "[" + textValue+"]";
        }
        return "Start " + action + textValue + " element with locator " + elementLocator + " " + tail;
    }
    //SET LOG MESSAGE: END ACTION Successfully
    public static String setEndMessageSuccess(String action, String textValue, String elementLocator, String tail){
        if (!textValue.equals("")){
            textValue = "[" + textValue+"]";
        }
        return "Success-End " + action + " " + textValue + " element with locator " + elementLocator + " " + tail;
    }
    //SET LOG MESSAGE: END ACTION FAILED
    public static String setEndMessageFail(String action, String textValue, String elementLocator, String tail){
        if (!textValue.equals("")){
            textValue = "[" + textValue+"]";
        }
        return "Failed-End " + action + " " + textValue + " element with locator " + elementLocator + " " + tail;
    }
}
