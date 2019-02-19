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

    private WebDriver driver;
    private WebDriverWait wait;

    private String emptyValue = "";
    private String startMessage;
    private String endMessage_PASS;
    private String endMessage_FAIL;

    /**
     * CONSTRUCTORS
     */

    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.setDriver(driver);
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    /**
     * Getters and Setters
     */

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Set Up Start - End Message automatically
     */
    private void setUpLoggingMessage(String nameOfMethod, WebElement element, String textValue) {
        String methodName = nameOfMethod.toLowerCase();
        setActionLoggerMessages(methodName,textValue, element);
        startMessage = getStartMessage();
        //SUCCESS END MESSAGE
        endMessage_PASS = getEndMessage_PASSED();
        //FAILED END MESSAGE
        endMessage_FAIL = getEndMessage_FAILED();

    }


    protected void navigateToPage(String url) {
        setUpLoggingMessage(getMethodName(), null, url);
        Log.info(startMessage);
        try {
            Browser.goToPage(url);
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    //Hover Mouse
    protected void hoverMouseToElement(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, emptyValue);
        Log.info(startMessage);
        Actions action = new Actions(getDriver());
        try {
            action.moveToElement(element).perform();
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.info(endMessage_FAIL);
        }
    }

    // Click with Javascript
    protected void jsClick(WebElement element) {
        setUpLoggingMessage(getMethodName(), element, emptyValue);
        Log.info(startMessage);
        try {
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", element);
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
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
        waitForElementToAppear(element);
        setUpLoggingMessage(getMethodName(), element, emptyValue);
        Log.info(startMessage);
        try {
            element.click();
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    // Set Text
    protected void setText(WebElement element, String text) {
        waitForElementToAppear(element);
        setUpLoggingMessage(getMethodName(), element, text);
        Log.info(startMessage);
        try {
            element.sendKeys(text);
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    /**
     * @param element
     * @return
     */
    // Get Text
    protected String getText(WebElement element) {
        waitForElementToAppear(element);
        String textOfElement;
        setUpLoggingMessage(getMethodName(), element, emptyValue);
        Log.info(startMessage);
        try {
            textOfElement = element.getText();
            Log.info(endMessage_PASS);
            Log.info("Text of Element: [" + textOfElement + "]");
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
            return null;
        }
        return textOfElement;
    }

    //Assert Equals Text
    protected void assertText(WebElement element, String expectedText) {
        waitForElementToAppear(element);
        setUpLoggingMessage(getMethodName(), element, expectedText);
        Log.info(startMessage);
        try {
            Assert.assertEquals(getText(element), expectedText);
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    //Assert Equals Number
    protected void assertNumber(WebElement element, int expectedNumber) {
        waitForElementToAppear(element);
        String expectedNumberS = String.valueOf(expectedNumber);
        setUpLoggingMessage(getMethodName(), element, expectedNumberS);
        Log.info(startMessage);
        try {
            Assert.assertEquals(Integer.parseInt(getText(element)), expectedNumber);
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    /**
     * @return title
     */
    // Get Page Title
    protected String getPageTitle() {
        String title;
        setUpLoggingMessage(getMethodName(), null, emptyValue);
        Log.info(startMessage);
        try {
            title = getDriver().getTitle();
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
            return null;
        }
        return title;
    }

    // Back to Previous Page
    protected void backToPreviousPage() {
        setUpLoggingMessage(getMethodName(), null, emptyValue);
        Log.info(startMessage);
        try {
            getDriver().navigate().back();
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    //Wait for page load
    protected void waitForPageLoad() {
        setUpLoggingMessage(getMethodName(), null, emptyValue);
        Log.info(startMessage);
        Function<WebDriver, Boolean> functionWaitForPageLoad = new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(500);
            wait.until(functionWaitForPageLoad);
            Log.info(endMessage_PASS);
        } catch (Exception error) {
            Log.error(endMessage_FAIL, error);
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
        setUpLoggingMessage(getMethodName(), element, emptyValue);
        Log.info(startMessage);
        Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.error(endMessage_FAIL, e);
                }
                return element.isDisplayed() ? true : null;
            }
        };
        wait.until(function);
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
        setUpLoggingMessage(getMethodName(), element, emptyValue);
        Log.info(startMessage);
        Function<WebDriver, Boolean> invisibility = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
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
        setUpLoggingMessage(getMethodName(), null, emptyValue);
        Log.info(startMessage);
        try {
            Assert.assertTrue(element.isDisplayed());
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
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
        Function<WebDriver, Boolean> clickable = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                waitForElementToAppear(element);
                setUpLoggingMessage(getMethodName(), null, emptyValue);
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
    }

    /**
     * @param element Use to scroll to a visible element
     */
    protected void scrollToElement(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, emptyValue);
        waitForElementToAppear(element);
        Actions actions = new Actions(getDriver());

        Log.info(startMessage);
        try {
            actions.moveToElement(element);
            actions.perform();
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

    /**
     * @param element Use to Check a checkbox element
     */
    protected void check(WebElement element) {
        setUpLoggingMessage(getMethodName(), null, emptyValue);
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
        waitForElementToAppear(element);

        setUpLoggingMessage(getMethodName(), null, emptyValue);
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
        waitForElementToAppear(ddlElement);
        setUpLoggingMessage(getMethodName(), null, emptyValue);
        Log.info(startMessage);
        Select selectReason = new Select(ddlElement);
        try {
            selectReason.selectByVisibleText(option);
            Log.info(endMessage_PASS);
        } catch (Exception e) {
            Log.error(endMessage_FAIL, e);
        }
    }

}
