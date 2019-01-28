package com.hansencx.solutions.portal.pages.servicecenter.update;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/24/2019
 */


public class WaitMessageDialog extends BasePage {
    public WaitMessageDialog(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "PopupWaitMessage")
    private WebElement boxWaitMessage;
    public void waitForMessageDismiss(){
        waitForElementToDisappear(boxWaitMessage);
    }
}
