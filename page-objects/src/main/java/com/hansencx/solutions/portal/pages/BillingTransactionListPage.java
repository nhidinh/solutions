package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillingTransactionListPage extends BasePage {
    /**
     * Constructors
     */
    public BillingTransactionListPage(WebDriver driver) {
        super(driver);
    }

    //HUONG:25.01.19: click on origin and cancel trans
    public void clickOnTransText(String originTranId) {
        String locator = "//a[@href='PndTranView.aspx?tranID=" + originTranId + "']";
        click(getDriver().findElement(By.xpath(locator)));
    }
}
