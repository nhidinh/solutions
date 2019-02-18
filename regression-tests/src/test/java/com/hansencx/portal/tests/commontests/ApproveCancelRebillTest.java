package com.hansencx.portal.tests.commontests;

import com.hansencx.portal.tests.core.PortalBaseTest;

import java.util.ArrayList;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 2/18/2019
 */


public class ApproveCancelRebillTest extends PortalBaseTest {
    public static void approveCancelRebillTest(String createdTime, ArrayList<String> listTransactionID) {
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
    }
}
