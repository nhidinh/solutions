package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import com.hansencx.solutions.portal.utilities.PortalDatabaseSupplierDict;
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
//    WebElement processButton;

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
        waitForPageLoad();
    }
    //HUONG:25.01.19:Update method for mapping between database & web present
    public void selectSupplierByKySupplier(String supplierKyName){
        String supplierName = PortalDatabaseSupplierDict.getValue(supplierKyName);
        Select selectSupplierName = new Select(lstSupplierName);
        selectSupplierName.selectByVisibleText(supplierName);
        waitForPageLoad();

    }
    //ACTIONS
    public void searchByEnrollmentNumberWithFilter(String option, String enrollNumber){
        selectEnrollmentNumberFilterOption(option);
        setTextEnrollmentNumber(enrollNumber);
    }
}
