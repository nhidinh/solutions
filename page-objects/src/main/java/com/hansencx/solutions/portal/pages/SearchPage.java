package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import com.hansencx.solutions.portal.utilities.PortalDatabaseSupplierDict;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver){
        super(driver);
    }



    //ELEMENTS
    @FindBy(id = "SearchButton")
    WebElement btnSearch;
    @FindBy(id="ResetButton")
    WebElement btnClear;
    @FindBy(name = "EnrollmentNumberFilter")
    WebElement ddlEnrollmentNumberFilter;
//    @FindBy(id="EnrollmentNumber")
    @FindBy(xpath = "//*[contains(@data-bind,\"EnrollmentNumber.Value\")]")
    WebElement txtEnrollmentNumber;
    @FindBy(id = "Suppliers")
    WebElement lstSupplierName;
    @FindBy(xpath = "//select[@class=\"NavigationSelect\"]")
    WebElement EnrollmentView;
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
    //METHODS
    public void clickSearchButton(){
        click(btnSearch);
        waitForPageLoad();
    }
    public void clickClearButton(){
        click(btnClear);
    }

    public void setTextEnrollmentNumber(String enrollNumber){
        setText(txtEnrollmentNumber, enrollNumber);
    }

    public void selectEnrollmentNumberFilterOption(String option){
        Select selectEnrollmentNumberFilter = new Select(ddlEnrollmentNumberFilter);
        selectEnrollmentNumberFilter.selectByVisibleText(option);
    }

    public void selectSupplierByName(String supplierName){
        Select selectSupplierName = new Select(lstSupplierName);
        selectSupplierName.selectByVisibleText(supplierName);
    }
    //HUONG:25.01.19:Update method for mapping between database & web present
    public void selectSupplierByKySupplier(String supplierKyName){
        Select selectSupplierName = new Select(lstSupplierName);
        String supplierName = PortalDatabaseSupplierDict.getValue(supplierKyName);
        System.out.println("supplierName: " + supplierName);
        selectSupplierName.selectByVisibleText(supplierName);
    }
    //ACTIONS
    public void searchByEnrollmentNumberWithFilter(String option, String enrollNumber){
        selectEnrollmentNumberFilterOption(option);
        setTextEnrollmentNumber(enrollNumber);
    }
    //HUONG:25.01.19: billing transaction interface
    public void selectViewFromEnrollment(String enrollmentView){
        Select selectView = new Select(EnrollmentView);
        selectView.selectByVisibleText(enrollmentView);
    }

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
    public void clickOnBackBillingTransList(){
        click(backToPendingTranListing);
    }
    //HUONG:25.01.19: get text on validate button
    public String getTextErrorWorkList(){
        return getText(errorworkList);
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
