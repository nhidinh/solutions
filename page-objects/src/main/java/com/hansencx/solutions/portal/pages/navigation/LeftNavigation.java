package com.hansencx.solutions.portal.pages.navigation;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftNavigation extends BasePage {
    public LeftNavigation(WebDriver driver) {
        super(driver);
    }

    //ELEMENTS
    @FindBy(id = "menuButton")
    private WebElement btnMenu;

    //METHODS
    public void clickMenuButton() {
        click(btnMenu);
    }

    //============================SERVICE CENTER============================//
    //ELEMENT OF SERVICE CENTER MENU:
    @FindBy(xpath = "//div[contains(@id,'menuHolder')]//a[text()='Service Center']")
    private WebElement lnkServiceCenter;
    @FindBy(xpath = "//a[text()='Service Center']//parent::li//a[text()='Update']")
    private WebElement lnkUpdate;
    @FindBy(xpath = "//a[text()='Service Center']//parent::li//a[text()='History']")
    private WebElement lnkHistory;
    @FindBy(xpath = "//a[text()='Service Center']//parent::li//a[text()='Approvals']")
    private WebElement lnkApproval;

    /////METHODS OF SELECTING SERVICE CENTER MENU
    public void clickServiceCenter() {
        click(lnkServiceCenter);
    }

    public void clickServiceCenterUpdateMenu() {
        click(lnkUpdate);
    }

    public void clickServiceCenterHistoryMenu() {
        click(lnkHistory);
    }

    public void clickServiceCenterApprovalsMenu() {
        click(lnkApproval);
    }
    //============================SERVICE CENTER============================//

}

