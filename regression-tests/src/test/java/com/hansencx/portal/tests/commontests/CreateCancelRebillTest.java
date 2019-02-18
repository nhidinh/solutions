package com.hansencx.portal.tests.commontests;

import com.hansencx.portal.tests.core.PortalBaseTest;

import java.util.ArrayList;
import java.util.Map;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 2/15/2019
 */


public class CreateCancelRebillTest extends PortalBaseTest {
    private static ArrayList<String> listTransactionID = new ArrayList<String>();
    private static String createdTime;

    public static void createCancelRebillTest(String importedFilePath) {
        //STEP 2.e.	Click on Update
        Page.LeftNavigation().clickServiceCenterUpdateMenu();
        //STEP 2.f.	Click on Add New:
        Page.ServiceCenterUpdate().clickAddNewButton();
        //STEP 2.g.	Scroll down to the Custpro area of Service Center templates
        //click the checkbox for Create Cancel Rebill
        // and click on the Import button.
        Page.AddServiceCenterUpdate().scrollToCustproArea();
        Page.AddServiceCenterUpdate().checkCreateCancelRebillCheckbox();
        Page.AddServiceCenterUpdate().clickImportButton();
        //STEP 2.k.	Upload the saved Create Cancel Rebill template.
        Page.ImportServiceCenterUpdate().uploadCancelRebillFile(importedFilePath);
        Page.ImportServiceCenterUpdate().clickUploadButton();
        //STEP 2.l.	Verify the template is uploaded and the data appears on the screen without errors
        // then click on the Process button
        Page.CreateCancelRebill().verifyUploadSuccessfullyWithNoError();
        int numberOfUploadedRecord = Page.CreateCancelRebill().getNumberOfRecord();
        listTransactionID = Page.CreateCancelRebill().getListOfTransactionID();
        Page.CreateCancelRebill().clickProcessButton();
        //STEP 2.m Select other from the drop down
        //enter a comment of Regression Testing
        // and click on Ok
        Page.EnterReasonForProcess().selectReason("Other");
        Page.EnterReasonForProcess().setTextComment("Regression Testing");
        Page.EnterReasonForProcess().clickOkButton();
        createdTime = Page.EnterReasonForProcess().getCreatedTime();
        //Waiting for Process Popup message dismiss
        Page.WaitMessageDialog().waitForMessageDismiss();
        //Verify the "Update moved to Service Center Approval Queue" message appears
        Page.PortalDialog().waitForPopupMessageBox();
        Page.PortalDialog().verifyMessageConfirm("Update moved to Service Center Approval Queue");
        Page.PortalDialog().clickOkButton();
    }

    public static String getCreatedTime() {
        return createdTime;
    }

    public static ArrayList<String> getListTransactionID() {
        return listTransactionID;
    }
}
