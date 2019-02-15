package com.hansencx.solutions.core;

import com.hansencx.solutions.logger.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.helper.Browser;

import java.util.function.Function;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static utilities.helper.ActionLogger.*;


/**
 * BasePage class
 * All Page objects created should inherit this class
 *
 * @author Vi Nguyen, Nhi Dinh
 * @version 1.0
 * @since 2018-12-03
 */
public class BasePage {
    private static final int TIMEOUT = 30; //seconds
    private static final int POLLING = 100; //milliseconds

    protected WebDriver driver;
    private WebDriverWait wait;

    private String value = "";
    private String tail = "";
    private String startMessage;
    private String endMessage_PASS;
    private String endMessage_FAIL;

    /**
     * CONSTRUCTORS
     */

    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    private String getLocatorOfElement(WebElement element) {
        String elementName = element.toString();
        int index = elementName.indexOf("> ") + 2;
        return "[" + elementName.substring(index);
    }


    private String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Set Up Start - End Message automatically
     */
    private void setUpLoggingMessage(String nameOfMethod, WebElement element, String textValue, String tailValue) {
        String methodName = nameOfMethod.toLowerCase();
        String elementLocator = "";
        if (element != null) {
            elementLocator = getLocatorOfElement(element);
        }
        startMessage = setStartMessage(methodName, textValue, elementLocator, tailValue);
        //SUCCESS END MESSAGE
        endMessage_PASS = setEndMessageSuccess(methodName, textValue, elementLocator, tailValue);
        //FAILED END MESSAGE
        endMessage_FAIL = setEndMessageFail(methodName, textValue, elementLocator, tailValue);

        //Re passing empty value for @value and @tail;
        value = "";
        tail = "";
    }

    protected void navigateToPage(String url) {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        Log.info(startMessage);
        try {
            Browser.goToPage(url);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    //Hover Mouse
    protected void hoverMouseToElement(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, value, tail);
        Log.info(startMessage);
        Actions action = new Actions(driver);
        try {
            action.moveToElement(element).perform();
        } catch (Exception e) {
            Log.info(endMessage_FAIL);
        }
        Log.info(endMessage_PASS);
    }

    // Click with Javascript
    protected void jsClick(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, value, tail);
        Log.info(startMessage);
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    /**
     * Click on given element
     *
     * @param element the given web element
     * @return Nothing
     * @author Vi Nguyen
     * @since 2018-12-03
     */
    protected void click(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, value, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            element.click();
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    // Set Text
    protected void setText(WebElement element, String text) {
        setUpLoggingMessage(getMethodName(), element, text, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            element.sendKeys(text);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    /**
     * @param element
     * @return
     */
    // Get Text
    protected String getText(WebElement element) {
        String textOfElement;
        setUpLoggingMessage(getMethodName(), element, value, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            textOfElement = element.getText();
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
            return null;
        }
        Log.info(endMessage_PASS);
        Log.info("Text of Element: [" + textOfElement + "]");
        return textOfElement;
    }

    //Assert Equals Text
    protected void assertText(WebElement element, String expectedText) {
        setUpLoggingMessage(getMethodName(), element, expectedText, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            Assert.assertEquals(getText(element), expectedText);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    //Assert Equals Number
    protected void assertNumber(WebElement element, int expectedNumber) {
        String expectedNumberS = String.valueOf(expectedNumber);
        setUpLoggingMessage(getMethodName(), element, expectedNumberS, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            Assert.assertEquals(Integer.parseInt(getText(element)), expectedNumber);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    /**
     * @return title
     */
    // Get Page Title
    protected String getPageTitle() {
        String title;
        setUpLoggingMessage(getMethodName(), null, value, tail);
        Log.info(startMessage);
        try {
            title = driver.getTitle();
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
            return null;
        }
        Log.info(endMessage_PASS);
        return title;
    }

    // Back to Previous Page
    protected void backToPreviousPage() {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        Log.info(startMessage);
        try {
            driver.navigate().back();
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }

    //Wait for page load
    protected void waitForPageLoad() {
        Function<WebDriver, Boolean> functionWaitForPageLoad = new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
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
     *
     * @param locator the locator.
     * @return Nothing.
     */
    protected WebElement waitForElementToAppear(By locator) {
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
     *
     * @param element the Webelement.
     * @return Nothing.
     */
    protected void waitForElementToAppear(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, value, tail);
        Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Log.info(startMessage);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.error(endMessage_FAIL, e);
                }
                return element.isDisplayed() ? true : null;
            }
        };
        wait.until(function);
        Log.info(endMessage_PASS);
    }

    /**
     * Waits for the given element to disappear.
     *
     * @param element the Webelement.
     * @return Nothing.
     * @see
     * @since 2018-12-03
     */
    protected void waitForElementToDisappear(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, value, tail);
        Function<WebDriver, Boolean> invisibility = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Log.info(startMessage);
                try {
                    return !element.isDisplayed();
                } catch (StaleElementReferenceException var2) {
                    Log.error(endMessage_FAIL);
                    return true;
                }
            }
        };
        wait.until(invisibility);
    }

    protected void verifyElementPresent(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        Log.info(startMessage);
        try {
            Assert.assertTrue(element.isDisplayed());
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);
    }


    /**
     * Wait for element is clickable
     *
     * @param element the given web element
     * @return Nothing
     * @author Vi Nguyen
     * @see
     * @since 2018-12-03
     */
    protected void waitForElementToBeClickable(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, value, "is clickable");
        Function<WebDriver, Boolean> clickable = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                waitForElementToAppear(element);
                Log.info(startMessage);
                try {
                    return element != null && element.isEnabled() ? true : null;
                } catch (StaleElementReferenceException e) {
                    Log.error(endMessage_FAIL, e);

                    return null;
                }
            }
        };
        wait.until(visibilityOf(element));
        Log.info(endMessage_PASS);
    }

    /**
     * @param element Use to scroll to a visible element
     */
    protected void scrollToElement(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        waitForElementToAppear(element);
        Actions actions = new Actions(driver);

        Log.info(startMessage);
        try {
            actions.moveToElement(element);
            actions.perform();
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
        Log.info(endMessage_PASS);


    }

    /**
     * @param element Use to Check a checkbox element
     */
    protected void check(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            if (!element.isSelected()) {
                element.click();
                Log.info(endMessage_PASS);
            } else {
                Log.error("Element is already checked");
            }
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    /**
     * @param element Use to Check a checkbox element
     */
    protected void unCheck(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        waitForElementToAppear(element);
        Log.info(startMessage);
        try {
            if (element.isSelected()) {
                element.click();
                Log.info(endMessage_PASS);
            } else {
                String message = "Element is already unchecked";
                Log.error(message);
            }
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    protected void selectOptionByText(WebElement ddlElement, String option) {
        setUpLoggingMessage(getMethodName(), null, value, tail);
        waitForElementToAppear(ddlElement);
        Log.info(startMessage);
        Select selectReason = new Select(ddlElement);
        try {
            selectReason.selectByVisibleText(option);
            Log.error(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }
}
