package com.hansencx.solutions.portal.pages.servicecenter.update;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.helper.UploadFileHelper;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/22/2019
 */


public class ServiceCenterUpdatePage extends BasePage {
    public ServiceCenterUpdatePage(WebDriver driver){
        super(driver);
    }
    /////ELEMENTS
    @FindBy(xpath = "//input[@value = 'Add New']")
    WebElement btnAddNew;

    public void clickAddNewButton(){
        click(btnAddNew);
        waitForPageLoad();
    }

}
