package com.hansencx.solutions.portal.pages.navigation;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopNavigation extends BasePage {
    public TopNavigation(WebDriver driver){
        super(driver);
    }

    //WEB ELEMENTS
    @FindBy(xpath = "//header//a[contains(@id, 'Search')]")
    WebElement btnSearch;
    @FindBy(xpath = "//header//a[contains(@id, 'Home')]")
    WebElement btnHome;
    @FindBy(xpath = "//header//a[contains(@id, 'Logout')]")
    WebElement btnLogout;


    //METHODS
    public void clickSearchButton(){
        click(btnSearch);
        waitForPageLoad();
    }
    public void clickLogoutButton(){
        click(btnLogout);
    }
    public void clickHomeButton(){
        click(btnHome);
    }
}
