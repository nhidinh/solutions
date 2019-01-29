package com.hansencx.solutions.portal.pages.servicecenter.history;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/24/2019
 */


public class ServiceCenterHistoryPage extends BasePage {
    public ServiceCenterHistoryPage(WebDriver driver) {
        super(driver);
    }

    //ELEMENT

    private WebElement getLinkQueue(String createdTime) {
        String lnkQueueXpath = "//div[@class='columnValue requestedOn' and @title='" + createdTime + "']//parent::div//div[@class= 'columnValue queue']//a";
        WebElement lnkQueue = driver.findElement(By.xpath(lnkQueueXpath));
        return lnkQueue;
    }
    private WebElement getLinkStatus(String createdTime){
        String lnkStatusXpath = "//div[@class='columnValue requestedOn' and @title='" + createdTime + "']//parent::div//div[@class= 'columnValue status']";
        WebElement lnkStatus = driver.findElement(By.xpath(lnkStatusXpath));
        return lnkStatus;
    }

    public void selectCreatedRecordByCreatedTime(String createdTime){
        WebElement lnkQueue = getLinkQueue(createdTime);
        click(lnkQueue);
        waitForPageLoad();
    }
    public void verifyStatusIsApproved(String createdTime){
        WebElement lnkStatus = getLinkStatus(createdTime);
        assertText(lnkStatus, "Approved");
    }
    public void verifyStatusIsWaitingForApproval(String createdTime){
        WebElement lnkStatus = getLinkStatus(createdTime);
        assertText(lnkStatus, "Awaiting Approval");

    }
}
