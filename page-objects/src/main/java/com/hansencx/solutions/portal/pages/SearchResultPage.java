package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class SearchResultPage extends BasePage {
    public SearchResultPage(WebDriver driver){
        super(driver);
    }

    //ELEMENTS
    @FindBy(xpath = "//table[@id='gvSearchResults']//tr")
    List<WebElement> lstResult;

    public int getNumberOfResult(){
        return lstResult.size();
    }

}
