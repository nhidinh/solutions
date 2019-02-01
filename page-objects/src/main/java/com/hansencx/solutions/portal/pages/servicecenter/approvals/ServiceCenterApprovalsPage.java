package com.hansencx.solutions.portal.pages.servicecenter.approvals;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/29/2019
 */


public class ServiceCenterApprovalsPage extends BasePage {
    public ServiceCenterApprovalsPage(WebDriver driver) {
        super(driver);
    }
    //VARIABLES

    //WEB ELEMENT
    @FindBy(xpath = "//div[@class='list']")
    WebElement listRequestedRecord;

    private WebElement getLinkQueue(String createdTime) {
        String lnkQueueXpath = "//div[@class='columnValue requestedOn' and contains(text(),'" + createdTime + "')]//parent::div//a[@class='columnValue queue']";
        return driver.findElement(By.xpath(lnkQueueXpath));
    }

    // METHODS
    public void waitForListAppear() {
        waitForElementToAppear(listRequestedRecord);
    }

    public void selectQueueRequestByCreatedTime(String createdTime) {
        WebElement lnkQueue = getLinkQueue(createdTime);
        click(lnkQueue);
        waitForPageLoad();
    }
}
