package com.hansencx.solutions.portal.pages.servicecenter.update;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/23/2019
 */

public class CreateCancelRebillPage extends BasePage {

    //WEB ELEMENTS
    @FindBy(xpath = "//input[@value='Process']")
    WebElement btnProcess;
    @FindBy(xpath = "//div[contains(text(),'Error Count')]//span")
    WebElement lblErrorCountNumber;
    @FindBy(xpath = "//div[contains(@class,'row') and not(contains(@data-bind,'service-center-staging-row'))]")
    List<WebElement> lstRecord;
    @FindBy(xpath = "//div[contains(@class,'row') and not(contains(@data-bind,'service-center-staging-row'))]//div[contains(@class,'columnValue clip')][1]//div[contains(@data-bind,'title: value')]")
    List<WebElement> lstTransactionID;

    /**
     * Constructors
     */
    public CreateCancelRebillPage(WebDriver driver) {
        super(driver);
    }

    //METHODS
    public int getTheErrorCountNumber() {
        String errorCountNo = getText(lblErrorCountNumber);
        return Integer.parseInt(errorCountNo);
    }

    public int getNumberOfRecord() {
        return lstRecord.size();
    }

    public void verifyUploadSuccessfullyWithNoError() {
        Assert.assertEquals(getTheErrorCountNumber(), 0);
    }

    public void clickProcessButton() {
        click(btnProcess);
    }

    public ArrayList<String> getListOfTransactionID() {
        ArrayList<String> listOfTransactionId = new ArrayList<>();

        for (WebElement record : lstTransactionID) {
            String getTextTitle = getText(record);
            listOfTransactionId.add(getTextTitle);
        }
        return listOfTransactionId;
    }

    /**
     * Get the String list of transaction ids
     *
     * @param
     * @return Nothing
     * @author Vi Nguyen
     * @see
     * @since 2018-01-30
     */
    public String getListTransactionID() {
        String listTransID = "";
        if (lstTransactionID.size() == 1)
            return lstTransactionID.get(0).getText();
        else if (lstTransactionID.size() > 1) {
            for (int i = 0; i < lstTransactionID.size() - 1; i++) {
                listTransID += getText(lstTransactionID.get(i)) + ",";
            }
            listTransID += getText(lstTransactionID.get(lstTransactionID.size() - 1));
        }
        return listTransID;
    }
}
