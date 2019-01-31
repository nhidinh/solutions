package com.hansencx.portal.tests;

import com.hansencx.portal.datatest.DataFilePathHandler;
import com.hansencx.solutions.portal.PortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/24/2019
 */


public class CancelRebillTests extends PortalBaseTest {
    String importedFilePath;
    @BeforeTest
    public void setupDataBeforeTest(){
        importedFilePath = DataFilePathHandler.CREATE_CANCEL_REBILL_PATH;
    }
    @Test
    public void testCreateCancelRebill(){
        //STEP 2.c.	Click on left navigation menu symbol
//        Page.LeftNavigation().clickMenuButton();
        //STEP 2.d.	Click on Service Center on the navigation list to expand sub areas of Service Center
//        Page.LeftNavigation().clickServiceCenter();
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
        ArrayList<String> listTransactionID = Page.CreateCancelRebill().getListOfTransactionID();


        Page.CreateCancelRebill().clickProcessButton();
        //STEP 2.m Select other from the drop down
        //enter a comment of Regression Testing
        // and click on Ok
        Page.EnterReasonForProcess().selectReason("Other");
        Page.EnterReasonForProcess().setTextComment("Regression Testing");
        Page.EnterReasonForProcess().clickOkButton();
        String createdTime = Page.EnterReasonForProcess().getCreatedTime();
        //Waiting for Process Popup message dismiss
        Page.WaitMessageDialog().waitForMessageDismiss();
        //Verify the "Update moved to Service Center Approval Queue" message appears
        Page.PortalDialog().waitForPopupMessageBox();
        Page.PortalDialog().verifyMessageConfirm("Update moved to Service Center Approval Queue");
        Page.PortalDialog().clickOkButton();

        //STEP 2.n.	Use the left navigation menu to go back to service center
        // and then select History to see the pending information
//        Page.LeftNavigation().clickMenuButton();
//        Page.LeftNavigation().clickServiceCenter();
        Page.LeftNavigation().clickServiceCenterHistoryMenu();
        Page.WaitMessageDialog().waitForMessageDismiss();

        System.out.println("Created Time: " + createdTime);
        Page.ServiceCenterHistory().verifyStatusIsWaitingForApproval(createdTime);
        Page.ServiceCenterHistory().selectCreatedRecordByCreatedTime(createdTime);
        Page.ServiceCenterHistoryDetails().verifyTheTransactionIDIsCorrect(listTransactionID);

        //STEP 2.o.	User will request another person to go the Approvals screen and approve the pending service center so it is processed.
        Page.TopNavigation().clickLogoutButton();
        Page.Login().goTo();
        Page.Login().logonWithEncodedCredential("QAAPPROVER", "NC9CDhQVFj8XFgFG");

        //STEP 2.p.i. Use the left navigation menu to go back to service center, and then select Approvals to see the pending information
        Page.LeftNavigation().clickServiceCenterApprovalsMenu();
        Page.WaitMessageDialog().waitForMessageDismiss();

        //STEP 2.q.	As different user than the one whom uploaded the Service Center, user will click on the Queue# link
        Page.ServiceCenterApprovals().waitForListAppear();
        Page.ServiceCenterApprovals().selectQueueRequestByCreatedTime(createdTime);
        Page.WaitMessageDialog().waitForMessageDismiss();

        //STEP 2.r.	User will click on Approve
        Page.ServiceCenterApprovalDetails().verifyListTransactionIDIsCorrect(listTransactionID);
        Page.ServiceCenterApprovalDetails().clickApproveButton();
        //and then click on Ok when the window pops up
        Page.EnterReasonForApprovalDialog().clickOKButton();

        //STEP 2.s.	View the service center processed successfully by going back to the service center History screen:
        Page.TopNavigation().clickLogoutButton();
        Page.Login().goTo();
        Page.Login().logonWithEncodedCredential("QAREQUESTER", "NC9CHQEUETUSBxwFXg==");

        Page.LeftNavigation().clickServiceCenterHistoryMenu();
        Page.WaitMessageDialog().waitForMessageDismiss();
        Page.ServiceCenterHistory().verifyStatusIsApproved(createdTime);

    }

}
