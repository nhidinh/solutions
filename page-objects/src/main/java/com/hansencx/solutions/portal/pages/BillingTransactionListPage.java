package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BillingTransactionListPage extends BasePage {
    @FindBy(id = "_ctl0__ctl0_primary_primary_ucPendingTranInfo_btnValidate")
    WebElement validateButton;
    @FindBy(id = "_ctl0__ctl0_primary_primary_lblNoErrorsExist")
    WebElement errorworkList;
    @FindBy(id = "_ctl0__ctl0_primary_primary_ucBillingAcctInfo_btnBackToPendingTranListing")
    WebElement backToPendingTranListing;
    @FindBy(id = "btnAbandon")
    WebElement abandonButton;
    @FindBy(id = "_ctl0__ctl0_txtComment")
    WebElement commentBox;
    @FindBy(xpath = "//*[@id=\"_ctl0__ctl0_BodyMaster\"]/div[8]/div[3]/div/button[1]/span")
    WebElement processButton;


    //HUONG:25.01.19: click on origin and cancel trans
    public void clickOnTransText(String originTranId){
        String locator = "//a[@href='PndTranView.aspx?tranID="+ originTranId + "']";
        click(driver.findElement(By.xpath(locator)));
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
    //HUONG:25.01.19: get text on validate button
    public void clickOnAbandonButton(){
        click(abandonButton);
    }
    public void handlingCommentBox(){
        setText(commentBox,"QA automation testing");
        click(processButton);
    }

}
