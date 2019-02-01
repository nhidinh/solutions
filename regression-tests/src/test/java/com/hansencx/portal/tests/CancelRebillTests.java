package com.hansencx.portal.tests;


import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.PortalBaseTest;
import com.hansencx.solutions.portal.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.configuration.InitialData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancelRebillTests extends PortalBaseTest{

    String importedFilePath;
    static ArrayList<String> listTransactionID = new ArrayList<String>();
    private DatabaseHelper db;
    private String cancelTransId = null;
    private String originalTransId = null;
    private ResultSet resultSet;

    @BeforeTest
    public void setupDataBeforeTest(){
        importedFilePath = InitialData.PARENT_DIR + "\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\datatest\\Create Cancel Rebill - Filled In Template.xlsx";
        db = new DatabaseHelper();
        db.createConnection("PSOLQ");
    }
    @Test
    public void testCreateCancelRebill(){
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
        String createdTime = Page.EnterReasonForProcess().getCreatedTime();
        //Waiting for Process Popup message dismiss
        Page.WaitMessageDialog().waitForMessageDismiss();
        //Verify the "Update moved to Service Center Approval Queue" message appears
        Page.PortalDialog().waitForPopupMessageBox();
        Page.PortalDialog().verifyMessageConfirm("Update moved to Service Center Approval Queue");
        Page.PortalDialog().clickOkButton();

        //STEP 2.n.	Use the left navigation menu to go back to service center
        // and then select History to see the pending information
        Page.LeftNavigation().clickServiceCenterHistoryMenu();
        Page.WaitMessageDialog().waitForMessageDismiss();

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
        if(!listTransactionID.isEmpty()) {
            for (String tranID : listTransactionID) {
                validateTransactionCancelRebill(tranID);
            }
        }

    }
    public void validateTransactionCancelRebill(String tranID) {
        System.out.println("tranid: "+tranID);
        //Verify the result
        //1. Check that there are a pair of new trans are returned
        String countLineQuery = "select count(ky_pnd_seq_trans) from custpro.cpm_pnd_tran_hdr NEW " +
                "where NEW.ky_enroll in(select OLD.ky_enroll from custpro.cpm_pnd_tran_hdr OLD " +
                "where OLD.ky_pnd_seq_trans = " + tranID +
                "and OLD.DT_PERIOD_START = NEW.DT_PERIOD_START " +
                "and OLD.DT_PERIOD_END = NEW.DT_PERIOD_END) " +
                "and NEW.cd_tran_status = 28";
        List<String> resultsCountLine = db.executeQueryReturnString(countLineQuery);

        Assert.assertEquals(resultsCountLine.get(0), "2");

        /**  2. verify that two rounds are returned with value following pair of value (00, 01) or (17,18)
         3. verify that KY_BA, ID_TRANS_REF_NUM_867, ID_TRANS_REF_NUM_810 are the same for process, origin and cancel
         **/
        String inputQueryNew = "select CD_PURPOSE from custpro.cpm_pnd_tran_hdr NEW " +
                "where NEW.ky_enroll in(select OLD.ky_enroll from custpro.cpm_pnd_tran_hdr OLD " +
                "where OLD.ky_pnd_seq_trans = " + tranID +
                "and OLD.DT_PERIOD_START = NEW.DT_PERIOD_START " +
                "and OLD.DT_PERIOD_END = NEW.DT_PERIOD_END) " +
                "and NEW.cd_tran_status = 28";

        List<String> rsNewCDPurpose = db.executeQueryReturnString(inputQueryNew);

        // process
        String ky_ba_ProcessedTran = querySpecificInfoInProcessedTrans("KY_BA", tranID);
        String id_trans_ref_867_ProcessedTran = querySpecificInfoInProcessedTrans("ID_TRANS_REF_NUM_867", tranID);
        String id_trans_ref_810_ProcessedTran = querySpecificInfoInProcessedTrans("ID_TRANS_REF_NUM_810", tranID);
        // cd_purpose = 00: origin
        String ky_ba_New00 = querySpecificInfoFollowingKyEnroll("KY_BA", tranID, "00");
        String id_trans_ref_867_New00 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_867", tranID, "00");
        String id_trans_ref_810_New00 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_810", tranID, "00");
        // cd_purpose = 01: cancel
        String ky_ba_New01 = querySpecificInfoFollowingKyEnroll("KY_BA", tranID, "01");
        String id_trans_ref_867_New01 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_867", tranID, "01");
        String id_trans_ref_810_New01 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_810", tranID, "01");

        // cd_purpose = 17: origin
        String ky_ba_New17 = querySpecificInfoFollowingKyEnroll("KY_BA", tranID, "17");
        String id_trans_ref_867_New17 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_867", tranID, "17");
        String id_trans_ref_810_New17 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_810", tranID, "17");
        // cd_purpose = 18: cancel
        String ky_ba_New18 = querySpecificInfoFollowingKyEnroll("KY_BA", tranID, "18");
        String id_trans_ref_867_New18 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_867", tranID, "18");
        String id_trans_ref_810_New18 = querySpecificInfoFollowingKyEnroll("ID_TRANS_REF_NUM_810", tranID, "18");

        if (rsNewCDPurpose.contains("01") && rsNewCDPurpose.contains("00")) {
            //verify for appearance of 00 and 01
            Assert.assertEquals(rsNewCDPurpose.get(0), "01");
            Assert.assertEquals(rsNewCDPurpose.get(1), "00");

            //verify for content cd_purpose = 00 against processed trans
            Assert.assertEquals(ky_ba_ProcessedTran, ky_ba_New00);
            Assert.assertEquals(id_trans_ref_867_ProcessedTran, id_trans_ref_867_New00);
            Assert.assertEquals(id_trans_ref_810_ProcessedTran, id_trans_ref_810_New00);

            //verify for content cd_purpose = 01 against processed trans
            Assert.assertEquals(ky_ba_ProcessedTran, ky_ba_New01);
            Assert.assertEquals(id_trans_ref_867_ProcessedTran, id_trans_ref_867_New01);
            Assert.assertEquals(id_trans_ref_810_ProcessedTran, id_trans_ref_810_New01);
            // store cancel & original id
            cancelTransId = "01";
            originalTransId = "00";

        } else if (rsNewCDPurpose.contains("17") && rsNewCDPurpose.contains("18")) {
            //verify for appearance of 17 and 18
            Assert.assertEquals(rsNewCDPurpose.get(0), "17");
            Assert.assertEquals(rsNewCDPurpose.get(1), "18");

            //verify for content cd_purpose = 17 against processed trans
            Assert.assertEquals(ky_ba_ProcessedTran, ky_ba_New17);
            Assert.assertEquals(id_trans_ref_867_ProcessedTran, id_trans_ref_867_New17);
            Assert.assertEquals(id_trans_ref_810_ProcessedTran, id_trans_ref_810_New17);

            //verify for content cd_purpose = 18 against processed trans
            Assert.assertEquals(ky_ba_ProcessedTran, ky_ba_New18);
            Assert.assertEquals(id_trans_ref_867_ProcessedTran, id_trans_ref_867_New18);
            Assert.assertEquals(id_trans_ref_810_ProcessedTran, id_trans_ref_810_New18);

            // store cancel & original id
            cancelTransId = "17";
            originalTransId = "18";
        } else {
            Assert.fail("Wrong output of cd_purpose: no valid value !");
        }
        //step 4:
        //1. get ky_enroll, ky_supplier
        String queryEnroll = "select ky_enroll from custpro.cpm_pnd_tran_hdr where ky_enroll in(select ky_enroll " +
                "from custpro.cpm_pnd_tran_hdr where ky_pnd_seq_trans =" + tranID +" )";
        String kyEnroll0 = db.executeQueryReturnString(queryEnroll).get(0);
        String kyEnroll1 = db.executeQueryReturnString(queryEnroll).get(1);
        //validate whether ky_enroll is the same
        Boolean enrollCheckFlag = kyEnroll0.equals(kyEnroll1);
        Assert.assertEquals(kyEnroll0,kyEnroll1);

        String querySupplier = "select ky_supplier from custpro.cpm_pnd_tran_hdr where ky_enroll in(select ky_enroll " +
                "from custpro.cpm_pnd_tran_hdr where ky_pnd_seq_trans =" + tranID +" )";
        String kySupplier0 = db.executeQueryReturnString(querySupplier).get(0);
        String kySupplier1 = db.executeQueryReturnString(querySupplier).get(1);
        //validate whether KY_SUPPLIER is the same
        Boolean supplierCheckFlag = kySupplier0.equals(kySupplier1);
        Assert.assertEquals(kySupplier0,kySupplier1);
        //2. log in PORTAL with acc : generic
        LoginPage loginPage = Page.Login().goTo();
        loginPage.logonWithEncodedCredential("QAGENERIC", "NC9CCAELASIIEEI=");

        //3. Search for ky_enroll
        Page.TopNavigation().clickSearchButton();
        if(enrollCheckFlag.equals(true) && supplierCheckFlag.equals(true)) {
            Page.Search().selectSupplierByKySupplier(kySupplier0);
            Page.Search().searchByEnrollmentNumberWithFilter("equals", kyEnroll0);
            Page.Search().clickSearchButton();
        }else
        {
            Assert.fail("Ky_enroll/ky_supplier is diffeent between cancel and original transaction ! " +
                    "\n enrollCheckFlag: " + enrollCheckFlag + "\n supplierCheckFlag: " + supplierCheckFlag);
        }

        //4. choose billing transaction interface view
        Page.SearchResult().selectViewFromEnrollment("Billing Transaction Interface");

        /** 5. click on cancel and original trans
         6. Click on validate button and verify no error
         NOTE: Validation should perform for Cancel first because of the order processing in Billing run,
         it should be cancel firstly to have original trans
         **/
        String kyPndSeqCancelTransNumber = querySpecificInfoFollowingKyEnroll("KY_PND_SEQ_TRANS", tranID, cancelTransId);
        String kyPndSeqOriginalTransNumber = querySpecificInfoFollowingKyEnroll("KY_PND_SEQ_TRANS", tranID, originalTransId);
        System.out.println("kyPndSeqCancelTransNumber: "+ kyPndSeqCancelTransNumber);
        System.out.println("kyPndSeqOriginalTransNumber: "+ kyPndSeqOriginalTransNumber);
        Page.BillingTransactionList().clickOnTransText(kyPndSeqCancelTransNumber);
        Page.BillingTransactionView().clickOnValidateButton();
        Page.BillingTransactionView().validateTransactionIsSuccessful();

        //verify status code in database after validation, should be 65 as sucessful validation
        String queryInputCancel = "select cd_tran_status from custpro.cpm_pnd_tran_hdr where ky_enroll ="
                + kyEnroll0 + " and ky_pnd_seq_trans =" + kyPndSeqCancelTransNumber;
        String cdTranStatusCodeOfCancel = db.returnQueriedStringField(queryInputCancel);
        Assert.assertEquals(cdTranStatusCodeOfCancel,"65");

        Page.BillingTransactionView().clickOnBackBillingTransList();

        Page.BillingTransactionList().clickOnTransText(kyPndSeqOriginalTransNumber);
        Page.BillingTransactionView().clickOnValidateButton();
        Page.BillingTransactionView().validateTransactionIsSuccessful();
        Page.BillingTransactionView().clickOnBackBillingTransList();

        //verify status code in database after validation, should be 65 as sucessful validation
        String queryInputOriginal = "select cd_tran_status from custpro.cpm_pnd_tran_hdr where ky_enroll ="
                + kyEnroll0 +" and ky_pnd_seq_trans =" + kyPndSeqOriginalTransNumber;
        String cdTranStatusCodeOfOriginal = db.returnQueriedStringField(queryInputOriginal);
        Assert.assertEquals(cdTranStatusCodeOfOriginal,"65");
    }
    private String querySpecificInfoFollowingKyEnroll(String queryLabel, String tranID, String purposeCode){
        String outputString = null;
        String queryInput = "select " + queryLabel + " from custpro.cpm_pnd_tran_hdr NEW " +
                "where NEW.ky_enroll in(select OLD.ky_enroll from custpro.cpm_pnd_tran_hdr OLD " +
                "where OLD.ky_pnd_seq_trans = " + tranID +
                "and OLD.DT_PERIOD_START = NEW.DT_PERIOD_START " +
                "and OLD.DT_PERIOD_END = NEW.DT_PERIOD_END) " +
                "and NEW.cd_tran_status = 28"+
                "and cd_purpose = "+ purposeCode;
        try {
            resultSet = db.getStatement().executeQuery(queryInput);
            while(resultSet.next()){
                outputString = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputString;
    }

    private String querySpecificInfoInProcessedTrans(String queryLabel, String tranID){
        String outputString = null;
        String queryInput = "select "+ queryLabel + " from custpro.cpm_pnd_tran_hdr where ky_pnd_seq_trans ="+ tranID;
        try {
            resultSet = db.getStatement().executeQuery(queryInput);
            while(resultSet.next()){
                outputString = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputString;
    }

}
