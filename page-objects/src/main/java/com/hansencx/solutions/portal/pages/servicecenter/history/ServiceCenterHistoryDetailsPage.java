package com.hansencx.solutions.portal.pages.servicecenter.history;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void verifyQueueNumberIsCorrect(int queueNumber){
        assertNumber(lblQueue, queueNumber);
    }
    public void verifyStatus(String status){
        assertText(lblStatus, status);
    }
    public int getNumberOfRecord(){
        return lstRecord.size();
    }
}
