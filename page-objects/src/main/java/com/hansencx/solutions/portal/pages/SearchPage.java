package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
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

    //ACTIONS
    public void searchByEnrollmentNumberWithFilter(String option, String enrollNumber){
        selectEnrollmentNumberFilterOption(option);
        setTextEnrollmentNumber(enrollNumber);
    }

}
