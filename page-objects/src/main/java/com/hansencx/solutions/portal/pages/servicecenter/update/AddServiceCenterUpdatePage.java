package com.hansencx.solutions.portal.pages.servicecenter.update;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/23/2019
 */


public class AddServiceCenterUpdatePage extends BasePage {
    /////WEB ELEMENT
    @FindBy(xpath = "//span[text()='Custpro']")
    WebElement lblCustpro;
    @FindBy(xpath = "//label[text()='Create Cancel Rebill']//parent::div//input")
    WebElement chkCreateCancelRebill;
    @FindBy(xpath = "//input[@value='Import']")
    WebElement btnImport;

    public void scrollToCustproArea(){
        scrollToElement(lblCustpro);
    }

    public void checkCreateCancelRebillCheckbox(){
        check(chkCreateCancelRebill);
    }

    public void clickImportButton(){
        click(btnImport);
    }

}
