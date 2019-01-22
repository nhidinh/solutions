package com.hansencx.solutions.portal.utilities;

import com.hansencx.solutions.core.BasePage;
import com.hansencx.solutions.portal.pages.HomePage;
import com.hansencx.solutions.portal.pages.LoginPage;
import com.hansencx.solutions.portal.pages.SearchPage;
import com.hansencx.solutions.portal.pages.SearchResultPage;
import com.hansencx.solutions.portal.pages.navigation.LeftNavigation;
import com.hansencx.solutions.portal.pages.navigation.TopNavigation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class PortalPageGenerator {
    public static WebDriver driver;

    public PortalPageGenerator(WebDriver driver){
        this.driver = driver;
    }

    private <TPage extends BasePage> TPage GetPage(Class<TPage> pageClass){
        return PageFactory.initElements(driver, pageClass);
    }

    public LoginPage Login() {
        return GetPage(LoginPage.class);
    }

    public HomePage Home(){
        return GetPage(HomePage.class);
    }
    public SearchPage Search(){
        return GetPage(SearchPage.class);
    }
    public SearchResultPage SearchResult(){
        return GetPage(SearchResultPage.class);
    }
    public TopNavigation TopNavigation(){
        return GetPage(TopNavigation.class);
    }
    public LeftNavigation LeftNavigation(){
        return GetPage(LeftNavigation.class);
    }
}
