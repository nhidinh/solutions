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
 * @since 1/23/2019
 */


public class ImportServiceCenterUpdatePage extends BasePage {
    public ImportServiceCenterUpdatePage(WebDriver driver){
        super(driver);
    }
    //WEB ELEMENTS
    @FindBy(xpath = "//div[@class='add']//a[text()='click here']")
    private WebElement lnkDownloadTemplate;
    @FindBy(xpath = "//input[@type='file']")
    private WebElement txt_UploadFile;
    @FindBy(xpath = "//input[@value='Upload']")
    private WebElement btnUpload;
    @FindBy(id = "WaitMessageBoxMessage")
    private WebElement boxWaitingMessage;

    //METHODS

    public void uploadCancelRebillFile(String filePath){
        UploadFileHelper uploadFile = new UploadFileHelper();
        uploadFile.uploadFile(filePath, txt_UploadFile);
    }

    public void clickUploadButton(){
        click(btnUpload);
        waitingForUploadMessageDismiss();
    }
    public void waitingForUploadMessageDismiss(){waitForElementToDisappear(boxWaitingMessage);}
}
