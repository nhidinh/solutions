package com.hansencx.solutions.portal.pages.servicecenter.history;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
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
 * @since 1/24/2019
 */


public class ServiceCenterHistoryDetailsPage extends BasePage {
    public ServiceCenterHistoryDetailsPage(WebDriver driver) {
        super(driver);
    }

    //VARIABLE
    String lstRecordXpath = "//div[contains(@class,'row') and not(contains(@data-bind,'stagingRecord'))]";


    //ELEMENTS
    @FindBy(xpath = "//*[contains(text(),'Queue #')]//parent::div//following-sibling::div")
    private WebElement lblQueue;
    @FindBy(xpath = "//*[contains(text(),'Update')]//parent::div//following-sibling::div")
    private WebElement lblUpdate;
    @FindBy(xpath = "//*[contains(text(),'Requested by')]//parent::div//following-sibling::div")
    private WebElement lblRequestedBy;
    @FindBy(xpath = "//*[contains(text(),'Requested on')]//parent::div//following-sibling::div")
    private WebElement lblRequestedOn;
    @FindBy(xpath = "//*[contains(text(),'Status')]//parent::div//following-sibling::div")
    private WebElement lblStatus;
    @FindBy(xpath = "//div[contains(@class,'row') and not(contains(@data-bind,'stagingRecord'))]")
    private List<WebElement> lstRecord;
    @FindBy(xpath = "//div[contains(@class,'row') and not(contains(@data-bind,'stagingRecord'))]//div[3]//span")
    private List<WebElement> lstTransactionID;

       public void verifyQueueNumberIsCorrect(int queueNumber){
        assertNumber(lblQueue, queueNumber);
    }
    public void verifyStatus(String status){
        assertText(lblStatus, status);
    }
    public int getNumberOfRecord(){
        return lstRecord.size();
    }
    public ArrayList<String> getListOfTransactionID(){
        ArrayList<String> listOfTransactionId = new ArrayList<>();
        for(WebElement transaction:lstTransactionID){
            String transactionID = getText(transaction);
            listOfTransactionId.add(transactionID);
        }
        return listOfTransactionId;
    }
    public void verifyTheTransactionIDIsCorrect(ArrayList<String> uploadedTransactionList){
        Assert.assertEquals(uploadedTransactionList, getListOfTransactionID());
    }
}
