package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import com.hansencx.solutions.portal.utilities.Links;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.helper.StringEncrypt;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver){
        super(driver);
    }

    //WEB ELEMENT
    @FindBy(xpath = "//table[contains(@id,'Login')]//input[contains(@id, 'UserName')]")
    WebElement txtUsername;
    @FindBy(xpath = "//table[contains(@id,'Login')]//input[contains(@id, 'Password')]")
    WebElement txtPassword;
    @FindBy(xpath = "//table[contains(@id,'Login')]//input[contains(@id, 'LoginButton')]")
    WebElement btnLogon;
    @FindBy(xpath = "//table[contains(@id,'Login')]//a[contains(text(),'Forgot your password')]")
    WebElement lnkForgotPassword;

    //MEDTHODS
    public void setTextUsername(String username){
        setText(txtUsername, username);
    }
    public void setTextPassword(String password){
        setText(txtPassword, password);
    }
    public void clickLogonButton(){
        click(btnLogon);
    }
    public void clickForgotPasswordLink(){
        click(lnkForgotPassword);
    }

    //ACTIONS
    public LoginPage goTo(){
        navigateToPage(Links.URL_LOGIN);
        return new LoginPage(driver);
    }
    public void logonWithUsername(String username, String password){
        waitForPageLoad();
        setTextUsername(username);
        setTextPassword(password);
        clickLogonButton();
    }

    public void logonWithEncodedCredential(String username, String encodedPassword){
        String key = "encodedPassword";
        String password = StringEncrypt.decryptXOR(encodedPassword, key);
        logonWithUsername(username, password);
    }
    public void verifyLogonSuccessfully(){}
}
