package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.helper.FailureHandling;

public class BillingTransactionViewPage extends BasePage {

    public BillingTransactionViewPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "_ctl0__ctl0_primary_primary_ucPendingTranInfo_btnValidate")
    private WebElement validateButton;

    @FindBy(id = "_ctl0__ctl0_primary_primary_ucBillingAcctInfo_btnBackToPendingTranListing")
    private WebElement backToPendingTranListing;

    @FindBy(id = "_ctl0__ctl0_txtComment")
    private WebElement commentAbandon;

    @FindBy(xpath = "//*[@id=\"_ctl0__ctl0_BodyMaster\"]/div[8]//span[.='Process']")
    private WebElement processAbandonButton;

    //Message - No Error Exist
    @FindBy(xpath = "//span[contains(@id, 'lblNoErrorsExist')]")
    private WebElement lblMessageNoError;

    public void setCommentAbandonBox(){
        setText(commentAbandon,"QA Automation Test");
    }

    public void clickOnValidateButton(){
        click(validateButton);
    }

    public void clickOnBackBillingTransList(){
        click(backToPendingTranListing);
    }

    public void handlingAbandonBox(){
        setCommentAbandonBox();
        waitForPageLoad();
        click(processAbandonButton);
        waitForPageLoad();
    }

    public void validateTransactionIsSuccessful(){
        try {
            verifyElementPresent(lblMessageNoError);
        }catch (AssertionError e){
            FailureHandling.continueAtFailedTestCase(e,"Validate Transaction Is Successfully");
        }
    }
}
