package com.hansencx.solutions.portal.pages.servicecenter.approvals;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/29/2019
 */


public class ServiceCenterApprovalDetailsPage extends BasePage {
    public ServiceCenterApprovalDetailsPage(WebDriver driver) {
        super(driver);
    }

    //WEB ELEMENTS
    @FindBy(xpath = "//input[@value='Approve']")
    private WebElement btnApprove;

    @FindBy(xpath = "//div[@class='columnValue' and contains(@data-bind,'stagingRecord.getStagingRecordDetail')][1]")
    private List<WebElement> lstRequestedRecord;

    //METHODS
    public ArrayList<String> getListTransactionID(){
        ArrayList<String> listTransactionID = new ArrayList<>();
        for(WebElement record:lstRequestedRecord){
            String transactionID = getText(record);
            listTransactionID.add(transactionID);
        }
        return listTransactionID;
    }
    public void verifyListTransactionIDIsCorrect(ArrayList<String> listTransactionID){
        Assert.assertEquals(listTransactionID, getListTransactionID());
    }
    public void clickApproveButton(){
        click(btnApprove);
    }

}
