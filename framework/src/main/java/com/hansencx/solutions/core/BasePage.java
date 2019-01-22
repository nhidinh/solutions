package com.hansencx.solutions.core;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;
import com.hansencx.solutions.logger.Log;
import utilities.helper.JavaScriptHelper;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
//import com.google.common.base.Function;
import java.util.function.Function;
/**
 * BasePage class
 * All Page objects created should inherit this class
 *
 * @author  Vi Nguyen, Nhi Dinh
 * @version 1.0
 * @since   2018-12-03
 */
public class BasePage {
    private static final int TIMEOUT = 30; //seconds
    private static final int POLLING = 100; //milliseconds

    protected WebDriver driver;
    private WebDriverWait wait;

    /**
     * CONSTRUCTORS
    */

    public BasePage(){}

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    private String getLocatorOfElement(WebElement element){
        String elementName = element.toString();
        int index = elementName.indexOf("> ") + 2;
        return "["+ elementName.substring(index);
    }

    //SET LOG MESSAGE: START ACTION
    private String setStartMessage(String action, String value, String elementLocator, String tail){
        if (!value.equals("")){
            value = "[" + value+"]";
        }
        return "Start " + action + value + " element with locator " + elementLocator + " " + tail;
    }
    //SET LOG MESSAGE: END ACTION
    private String setEndMessage(String action, String value, String elementLocator, String tail){
        if (!value.equals("")){
            value = "[" + value+"]";
        }
        return "End " + action + " " + value + " element with locator " + elementLocator + " " + tail;
    }


    public void navigateToPage(String url){
        Log.info("Navigating to " + url);
        try{
            driver.get(url);
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.error("Failed navigating to " + url);
            Log.error(e.toString());
        }
        Log.info("End of navigating to " + url + " step");
    }

    //Hover Mouse
    public void hoverMouseToElement(WebElement element){
        Actions action = new Actions(driver);
        String elementLocator = getLocatorOfElement(element) ;
        Log.info(setStartMessage("hovering mouse to: ", "",elementLocator,"" ));
        Log.info("Hovering mouse to element: ");
        try {
            ((Actions) action).moveToElement(element).perform();
            Log.info(setEndMessage("hovering mouse to", "", elementLocator, "successfully"));
        }catch (Exception e){
            Log.error(setEndMessage("hovering mouse to", "", elementLocator, "FAILED"));
        }
    }

    // Click with Javascript
    public void jsClick(WebElement ele) {
        String elementLocator = getLocatorOfElement(ele) ;
        Log.info(setStartMessage("clicking (JS) to","", elementLocator,""));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", ele);
        Log.info(setEndMessage("clicking (JS) to", "", elementLocator, ""));
    }

    /**
     * Click on given element
     * @author Vi Nguyen
     * @param element the given web element
     * @return Nothing
     * @since 2018-12-03
     * @see
     */
    public void click(WebElement element){
        String elementLocator = getLocatorOfElement(element);
        try {
            waitForElementToAppear(element);
            Log.info(setStartMessage("clicking to", "", elementLocator, ""));
            element.click();
            Log.info(setEndMessage("clicking to", "", elementLocator, ""));
        }catch (Exception e){
            Log.error("Unable to click element at: " + elementLocator);
            Log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    // Set Text
    public void setText(WebElement element, String text){
        String elementLocator = getLocatorOfElement(element) ;
        waitForElementToAppear(element);
        Log.info(setStartMessage("setting text:", text, elementLocator, ""));
        element.sendKeys(text);
        Log.info(setEndMessage("setting text:", text, elementLocator, ""));
    }

    // Get Text
    public String getText(WebElement element) {
        String elementLocator = getLocatorOfElement(element) ;
        waitForElementToAppear(element);
        Log.info(setStartMessage("getting text of", "", elementLocator, ""));
        String text = element.getText();
        Log.info(setEndMessage("getting text of", "", elementLocator, ""));
        return text;
    }

    //Assert Equals
    public void assertText(WebElement element, String expectedText) {
        String elementLocator = getLocatorOfElement(element) ;
        waitForElementToAppear(element);
        Log.info(setStartMessage("asserting text:", expectedText, elementLocator, ""));
        Assert.assertEquals(getText(element), expectedText);
        Log.info(setEndMessage("asserting text:", expectedText, elementLocator, ""));
    }

    // Get Page Title
    public String getPageTitle(){
        Log.info("Start getting page title");
        String title = driver.getTitle();
        Log.info("Return Page Title");
        return title;
    }

    // Back to Previous Page
    public void backToPreviousPage(){
        Log.info("Start backing to previous page");
        driver.navigate().back();
        Log.info("End backing to previous page");
    }

    //Wait for page load
    public void waitForPageLoad(){
        Function<WebDriver, Boolean> functionWaitForPageLoad  = new Function<WebDriver, Boolean>() {
            public Boolean apply( WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(500);
            wait.until(functionWaitForPageLoad);
        } catch (Exception error) {
            Log.error(error.getMessage());
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    /**
     * This is the method which waits for the element to exist.
     * @param locator the locator.
     * @return Nothing.
     */
    public WebElement waitForElementToAppear(By locator) {
        Function<WebDriver, WebElement> functionWait = new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        };
        return wait.until(functionWait);
    }

    /**
     * This is the method which waits for the given element to appear.
     * @param element the Webelement.
     * @return Nothing.
     */
    public void waitForElementToAppear(WebElement element){
        Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return element.isDisplayed() ? true : null;
            }
        };
        wait.until(function);
}

    /**
     * Waits for the given element to disappear.
     * @param element the Webelement.
     * @return Nothing.
     * @since 2018-12-03
     * @see
     */
    public void waitForElementToDisappear(WebElement element){
        Function<WebDriver, Boolean> invisibility = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return !element.isDisplayed();
                } catch (StaleElementReferenceException var2) {
                    return true;
                }
            }
        };
        wait.until(invisibility);
    }

    public void verifyElementPresent(WebElement element){
        Assert.assertTrue(element.isDisplayed());
    }


    /**
     * Wait for element is clickable
     * @author Vi Nguyen
     * @param element the given web element
     * @return Nothing
     * @since 2018-12-03
     * @see
     */
    public void waitForElementToBeClickable(WebElement element){
        String elementLocator = getLocatorOfElement(element) ;
        Log.info(setStartMessage("waiting for","", elementLocator, "is clickable"));

        Function<WebDriver, Boolean> clickable = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                waitForElementToAppear(element);
                try {
                    return element != null && element.isEnabled() ? true : null;
                } catch (StaleElementReferenceException var4) {
                    return null;
                }
            }
        };
        wait.until(visibilityOf(element));

        Log.info(setEndMessage("waiting for","",elementLocator ,"is clickable"));

    }
}
