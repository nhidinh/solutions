package com.hansencx.solutions.portal.pages.servicecenter.approvals;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/29/2019
 */


public class EnterReasonForApprovalDialog extends BasePage {
    public EnterReasonForApprovalDialog(WebDriver driver) {
        super(driver);
    }

    //WEB ELEMENTS:
    @FindBy(xpath = "//span[contains(text(),'Enter reason for Approval')]//ancestor::div[contains(@class,'ui-widget-content')]//span[text()='Ok']//parent::button")
    private WebElement btnOK;

    //METHOD
    public void clickOKButton() {
        click(btnOK);
    }
}
