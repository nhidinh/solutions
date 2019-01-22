package com.hansencx.solutions.portal.pages.navigation;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftNavigation extends BasePage {
    public LeftNavigation(WebDriver driver){
        super (driver);
    }

    //ELEMENTS
    @FindBy(id = "menuButton")
    WebElement btnMenu;

    @FindBy(xpath = "//div[contains(@id,'menuHolder')]//a[text()='Service Center']")
    WebElement lnkServiceCenter;

    //METHODS
    public void clickMenuButton(){
        click(btnMenu);
    }
    public void clickServiceCenter(){
        click(lnkServiceCenter);
    }
}
