package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.helper.FailureHandling;

public class BillingTransactionListPage extends BasePage {
    public BillingTransactionListPage(WebDriver driver){
        super(driver);
    }
    @FindBy(id = "_ctl0__ctl0_primary_primary_ucPendingTranInfo_btnValidate")
    WebElement validateButton;
    @FindBy(id = "//*[@id=\\\"_ctl0__ctl0_primary_PageWrapper\\\"]/div[4]/fieldset/span")
    WebElement errorworkList;
    @FindBy(id = "_ctl0__ctl0_primary_primary_ucBillingAcctInfo_btnBackToPendingTranListing")
    WebElement backToPendingTranListing;
    @FindBy(id = "btnAbandon")
    WebElement abandonButton;
    @FindBy(id = "_ctl0__ctl0_txtComment")
    WebElement commentAbandon;
//    @FindBy(xpath = "//*[@id=\"_ctl0__ctl0_txtComment\"]")
//    WebElement commentAbandon;
    @FindBy(xpath = "//*[@id=\"_ctl0__ctl0_BodyMaster\"]/div[8]//span[.='Process']")
    WebElement processAbandonButton;
    @FindBy(xpath = "//*[@id=\"_ctl0__ctl0_BodyMaster\"]/div[8]/div[3]/div/button[1]/span")
    WebElement processButton;
    @FindBy(xpath = "//*[@class='quicklaunch']//li/a[.='Search']")
    WebElement searchButton;
    //Error Table Element:
    @FindBy(xpath = "//table[@class='Grid error']")
    private WebElement tblError;

    //Message - No Error Exist
    @FindBy(xpath = "//span[contains(@id, 'lblNoErrorsExist')]")
    private WebElement lblMessageNoError;

    //HUONG:25.01.19: click on origin and cancel trans
    public void clickOnTransText(String originTranId){
        String locator = "//a[@href='PndTranView.aspx?tranID="+ originTranId + "']";
        System.out.println("locator: "+locator);
        click(driver.findElement(By.xpath(locator)));
    }
    public void setCommentAbandonBox(){
        setText(commentAbandon,"QA Automation Test");
    }
    //HUONG:25.01.19: click on validate button
    public void clickOnValidateButton(){
        click(validateButton);
    }
    //HUONG:25.01.19: get text on validate button
    public String getTextErrorWorkList(){
        return getText(errorworkList);
    }
    //HUONG:25.01.19: get text on validate button
    public void clickOnBackBillingTransList(){
        click(backToPendingTranListing);
    }
    public void handlingAbandonBox(){
        setCommentAbandonBox();
        waitForPageLoad();
        click(processAbandonButton);
        waitForPageLoad();
    }
    public void clickOnSearch(){
        click(searchButton);
    }
    public void validateTransactionIsSuccessfull(){
        try {
            verifyElementPresent(lblMessageNoError);
        }catch (AssertionError e){
            FailureHandling.continueAtFailedTestCase(e,"Validate Transaction Is Successfully");
        }
    }
}
