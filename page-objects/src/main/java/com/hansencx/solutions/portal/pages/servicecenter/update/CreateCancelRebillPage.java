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
    public CreateCancelRebillPage(WebDriver driver) {
        super(driver);
    }
    //WEB ELEMENTS
    @FindBy(xpath = "//input[@value='Process']")
    WebElement btnProcess;
    @FindBy(xpath = "//div[contains(text(),'Error Count')]//span")
    WebElement lblErrorCountNumber;
    @FindBy(xpath = "//div[contains(@class,'list area worklist')]//div[contains(@class,'row') and not(contains(@data-bind,'service-center-staging-row'))]")
    List<WebElement> lstRecord;
    @FindBy(xpath = "//div[2]//div[@class='columnValueValue']//div[contains(@data-bind,'title: value')]")
    WebElement lblRecordValue;
    //METHODS
    public int getTheErrorCountNumber(){
        String errorCountNo = getText(lblErrorCountNumber);
        return Integer.parseInt(errorCountNo);
    }
    public int getNumberOfRecord(){
        return lstRecord.size();
    }
    public void verifyUploadSuccessfullyWithNoError(){
        Assert.assertEquals(getTheErrorCountNumber(), 0);
    }
    public void clickProcessButton(){
        click(btnProcess);
    }

    public ArrayList<String> getTitle(){
        ArrayList<String> listOfTransactionId = new ArrayList<>();

        for(WebElement record:lstRecord){
            String getTextRecord = record.getText().substring(0,8);
            listOfTransactionId.add(getTextRecord);
        }
        System.out.println(listOfTransactionId);
        return listOfTransactionId;
    }

}
