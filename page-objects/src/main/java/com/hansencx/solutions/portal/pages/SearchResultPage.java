package com.hansencx.solutions.portal.pages;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

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
    @FindBy(xpath = "//select[@class=\"NavigationSelect\"]")
    WebElement EnrollmentView;

    public int getNumberOfResult(){
        return lstResult.size();
    }

    public  void verifySearchResult(String numberOfLine){
        Assert.assertEquals(Integer.toString(getNumberOfResult()),numberOfLine );
    }

    //HUONG:25.01.19: billing transaction interface
    public void selectViewFromEnrollment(String enrollmentView){
        Select selectView = new Select(EnrollmentView);
        waitForPageLoad();
        selectView.selectByVisibleText(enrollmentView);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
