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
    private static String actionObject="";
    private static String dataTextValue;

    private static void checkValue(String text,String elementLocator){
        if(!elementLocator.equals("")){
            actionObject = " element with locator ";
        }
        if (!text.equals("")){
            dataTextValue = " [" + text+"]";
        }else{
            dataTextValue="";
        }
    }

    public static String setStartMessage(String action, String textValue, String elementLocator, String tail){
        checkValue(textValue, elementLocator);
        return "Start action " + action  + dataTextValue + actionObject + elementLocator + " " + tail;
    }
    //SET LOG MESSAGE: END ACTION Successfully
    public static String setEndMessageSuccess(String action, String textValue, String elementLocator, String tail){
        checkValue(textValue, elementLocator);
        return "Success-End action " + action + dataTextValue + actionObject + elementLocator + " " + tail;
    }
    //SET LOG MESSAGE: END ACTION FAILED
    public static String setEndMessageFail(String action, String textValue, String elementLocator, String tail){
        checkValue(textValue, elementLocator);
        return "Failed-End action " + action + dataTextValue + actionObject + elementLocator + " " + tail;
    }
}
