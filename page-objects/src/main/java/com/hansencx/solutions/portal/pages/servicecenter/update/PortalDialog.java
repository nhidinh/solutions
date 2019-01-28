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


public class PortalDialog extends BasePage {
    public PortalDialog(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "PopupMessageBox")
    private WebElement boxMessage;
    @FindBy(id = "MessageBoxMessage")
    private WebElement lblMessage;
    @FindBy(xpath = "//div[@id='AlertMessageDialog']//following-sibling::div//button")
    private WebElement btnOK;

    public void waitForPopupMessageBox(){
        waitForElementToAppear(boxMessage);
    }
    public void verifyMessageConfirm(String message){
        assertText(lblMessage, message);
    }
    public void clickOkButton(){
        click(btnOK);
    }
}
