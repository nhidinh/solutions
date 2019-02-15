package com.hansencx.solutions.portal.pages.servicecenter.update;

import com.hansencx.solutions.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/23/2019
 */


public class EnterReasonForProcessDialog extends BasePage {
    //ELEMENTS
    @FindBy(id = "ddlserviceCenterWFG")
    WebElement ddlServiceCenterWFG;
    @FindBy(xpath = "//span[text()='Enter reason for Process']//ancestor::div[contains(@role, 'dialog')]//textarea")
    WebElement txtComment;
    @FindBy(xpath = "//span[text()='Enter reason for Process']//ancestor::div[contains(@role, 'dialog')]//span[text()='Ok']//parent::button")
    WebElement btnOK;

    /**
     * Constructors
     */
    public EnterReasonForProcessDialog(WebDriver driver) {
        super(driver);
    }

    public void selectReason(String reason) {
        selectOptionByText(ddlServiceCenterWFG, reason);
    }

    public void setTextComment(String message) {
        setText(txtComment, message);
    }

    public void clickOkButton() {
        click(btnOK);
    }

    public String getCreatedTime() {
        String formatPattern = "MM/dd/yyyy hh:mm a";
        SimpleDateFormat dateFormater = new SimpleDateFormat(formatPattern);
        Date date = Calendar.getInstance().getTime();
        TimeZone portalServer = TimeZone.getTimeZone("EST");
        dateFormater.setTimeZone(portalServer);
        String currentPortalTime = dateFormater.format(date);
        return currentPortalTime;
    }

    /**
     * Do process
     *
     * @param reason, comment
     * @return nothing
     * @author Vi Nguyen
     * @see
     * @since 2019-01-30
     */
    public void doProcess(String reason, String comment) {
        selectReason(reason);
        setTextComment(comment);
        clickOkButton();
    }
}
