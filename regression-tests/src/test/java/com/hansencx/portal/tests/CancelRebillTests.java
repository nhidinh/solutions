package com.hansencx.portal.tests;


import com.hansencx.portal.tests.core.PortalBaseTest;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.configuration.InitialData;
import utilities.helper.SoftAssert;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancelRebillTests extends PortalBaseTest {

    private String importedFilePath;
    private static ArrayList<String> listTransactionID = new ArrayList<String>();
    private SoftAssert softAssert ;
    private DatabaseHelper db;
    private String cancelTransId = null;
    private String rebilledTransId = null;
    private ResultSet resultSet;
    String methodname = null;
    
    //CREDENTIAL DATA
    private String qageneric_username;
    private String qageneric_password;

    private String qaapprover_username;
    private String qaapprover_password;

    private String qarequester_username;
    private String qarequester_password;

    @BeforeTest
    public void setupDataBeforeTest(){
        importedFilePath = InitialData.PARENT_DIR + "\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\datatest\\Create Cancel Rebill - Filled In Template.xlsx";
        db = new DatabaseHelper();
        db.createConnection("PSOLQ");

    }
    @BeforeMethod
    public void setupbeforemethod(Method method){
        methodname = method.getName();
        softAssert = new SoftAssert(getDriver(), methodname );
        //CREDENTIAL DATA
        qageneric_username = Credential.getPropertyValue("qageneric_username");
        qageneric_password = Credential.getPropertyValue("qageneric_password");

        qaapprover_username = Credential.getPropertyValue("qaapprover_username");
        qaapprover_password = Credential.getPropertyValue("qaapprover_password");

        qarequester_username = Credential.getPropertyValue("qarequester_username");
        qarequester_password = Credential.getPropertyValue("qarequester_password");

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

        System.out.println("Created Time: " + createdTime);
        Page.ServiceCenterHistory().verifyStatusIsWaitingForApproval(createdTime);
        Page.ServiceCenterHistory().selectCreatedRecordByCreatedTime(createdTime);
//        Page.ServiceCenterHistoryDetails().verifyTheTransactionIDIsCorrect(listTransactionID);

        //STEP 2.o.	User will request another person to go the Approvals screen and approve the pending service center so it is processed.
        Page.TopNavigation().clickLogoutButton();
        Page.Login().goTo();
        Page.Login().logonWithEncodedCredential(qaapprover_username, qaapprover_password);

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
        Page.Login().logonWithEncodedCredential(qarequester_username, qarequester_password);

        Page.LeftNavigation().clickServiceCenterHistoryMenu();
        Page.WaitMessageDialog().waitForMessageDismiss();
        Page.ServiceCenterHistory().verifyStatusIsApproved(createdTime);
        System.out.println("tranId: "+listTransactionID.toString());
        if(!listTransactionID.isEmpty()) {
            for (String tranID : listTransactionID) {
                System.out.println("tranId: "+ tranID);
                validateTransactionCancelRebill(tranID);
            }
        }
        softAssert.assertAll();
    }
    private void validateTransactionCancelRebill(String tranID) {

        //Verify the result
        db.getSoftAssertObj(softAssert);
        //1. Check that there are a pair of new trans are returned
        String queryTotalLine = "select count(ky_pnd_seq_trans) from custpro.cpm_pnd_tran_hdr NEW " +
                "where NEW.ky_enroll in(select OLD.ky_enroll from custpro.cpm_pnd_tran_hdr OLD " +
                "where OLD.ky_pnd_seq_trans = " + tranID +
                "and OLD.DT_PERIOD_START = NEW.DT_PERIOD_START " +
                "and OLD.DT_PERIOD_END = NEW.DT_PERIOD_END) " +
                "and NEW.cd_tran_status = 28";
        String numberOfCreatedTransactions = db.executeQueryReturnString(queryTotalLine).get(0);
        db.checkResultBySoftAssert(numberOfCreatedTransactions,"2");

        /*  2. verify that two rounds are returned with value following pair of value (00, 01) or (17,18)
         3. verify that KY_BA, ID_TRANS_REF_NUM_867, ID_TRANS_REF_NUM_810 are the same for process, origin and cancel
         **/
        String queryCdPurposeList = "select CD_PURPOSE from custpro.cpm_pnd_tran_hdr NEW " +
                "where NEW.ky_enroll in(select OLD.ky_enroll from custpro.cpm_pnd_tran_hdr OLD " +
                "where OLD.ky_pnd_seq_trans = " + tranID +
                "and OLD.DT_PERIOD_START = NEW.DT_PERIOD_START " +
                "and OLD.DT_PERIOD_END = NEW.DT_PERIOD_END) " +
                "and NEW.cd_tran_status = 28";

        List<String> cdPurposeList = db.executeQueryReturnString(queryCdPurposeList);

        // process
        String kyBAProcessedTran = queryInfoInProcessedTrans("KY_BA", tranID);
        String idTransRef867ProcessedTran = queryInfoInProcessedTrans("ID_TRANS_REF_NUM_867",tranID);
        String idTransRef810ProcessedTran = queryInfoInProcessedTrans("ID_TRANS_REF_NUM_810", tranID);
        // cd_purpose = 00: origin
        String kyBARebilled00 = querySpecifiedInfoInCreatedTrans("KY_BA", "00",tranID);
        String idTransRef867Rebilled00 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_867", "00",tranID);
        String idTransRef810Rebilled00 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_810", "00",tranID);
        // cd_purpose = 01: cancel
        String kyBACancel01 = querySpecifiedInfoInCreatedTrans("KY_BA", "01",tranID);
        String idTransRef867Cancel01 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_867", "01",tranID);
        String idTransRef810Cancel01 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_810", "01",tranID);

        // cd_purpose = 17: origin
        String kyBARebilled17 = querySpecifiedInfoInCreatedTrans("KY_BA", "17",tranID);
        String idTransRef867Rebilled17 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_867", "17",tranID);
        String idTransRef810Rebilled17 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_810", "17",tranID);
        // cd_purpose = 18: cancel
        String kyBACancel18 = querySpecifiedInfoInCreatedTrans("KY_BA", "18",tranID);
        String idTransRef867Cancel18 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_867", "18",tranID);
        String idTransRef810Cancel18 = querySpecifiedInfoInCreatedTrans("ID_TRANS_REF_NUM_810", "18",tranID);

        if (cdPurposeList.contains("01") && cdPurposeList.contains("00")) {
            //verify for appearance of 00 and 01
           db.checkResultBySoftAssertWithMsg(cdPurposeList.get(0), "01","Appearance of CD_Purpose 01: ");
           db.checkResultBySoftAssertWithMsg(cdPurposeList.get(1), "00","Appearance of CD_Purpose 00: ");

            //verify for content cd_purpose = 00 against processed trans
           db.checkResultBySoftAssert(kyBAProcessedTran, kyBARebilled00);
           db.checkResultBySoftAssert(idTransRef867ProcessedTran, idTransRef867Rebilled00);
           db.checkResultBySoftAssert(idTransRef810ProcessedTran, idTransRef810Rebilled00);

            //verify for content cd_purpose = 01 against processed trans
           db.checkResultBySoftAssert(kyBAProcessedTran, kyBACancel01);
           db.checkResultBySoftAssert(idTransRef867ProcessedTran, idTransRef867Cancel01);
           db.checkResultBySoftAssert(idTransRef810ProcessedTran, idTransRef810Cancel01);
            // store cancel & original id
            cancelTransId = "01";
            rebilledTransId = "00";

        } else if (cdPurposeList.contains("17") && cdPurposeList.contains("18")) {
            //verify for appearance of 17 and 18
           db.checkResultBySoftAssertWithMsg(cdPurposeList.get(0), "17","Appearance of CD_Purpose 17: ");
           db.checkResultBySoftAssertWithMsg(cdPurposeList.get(1), "18", "Appearance of CD_Purpose 18: ");

            //verify for content cd_purpose = 17 against processed trans
           db.checkResultBySoftAssert(kyBAProcessedTran, kyBARebilled17);
           db.checkResultBySoftAssert(idTransRef867ProcessedTran, idTransRef867Rebilled17);
           db.checkResultBySoftAssert(idTransRef810ProcessedTran, idTransRef810Rebilled17);

            //verify for content cd_purpose = 18 against processed trans
           db.checkResultBySoftAssert(kyBAProcessedTran, kyBACancel18);
           db.checkResultBySoftAssert(idTransRef867ProcessedTran, idTransRef867Cancel18);
           db.checkResultBySoftAssert(idTransRef810ProcessedTran, idTransRef810Cancel18);

            // store cancel & original id
            cancelTransId = "17";
            rebilledTransId = "18";
        } else {
            Assert.fail("Wrong output of cd_purpose: no valid value !");
        }
        //step 4:

        //1. log in PORTAL with acc : generic
        LoginPage loginPage = Page.Login().goTo();
        loginPage.logonWithEncodedCredential(qageneric_username, qageneric_password);

        //2. Search for ky_enroll
        Page.TopNavigation().clickSearchButton();

        Boolean enrollMatchingFlag = enrollMatchingFlag(tranID);
        Boolean supplierMatchingFlag = supplierMatchingFlag(tranID);
        String kyEnroll = querySpecifiedInfoList("KY_ENROLL",tranID).get(0);
        String kySupplier = querySpecifiedInfoList("KY_SUPPLIER",tranID).get(0);

        if(enrollMatchingFlag.equals(true) && supplierMatchingFlag.equals(true)) {
            Page.Search().selectSupplierByKySupplier(kySupplier);
            Page.Search().searchByEnrollmentNumberWithFilter("equals",kyEnroll);
            Page.Search().clickSearchButton();
        }else
        {
            Assert.fail("Ky_enroll/ky_supplier is diffeent between cancel and original transaction ! " +
                    "\n enrollCheckFlag: " + enrollMatchingFlag + "\n supplierCheckFlag: " + supplierMatchingFlag);
        }

        //4. choose billing transaction interface view
        Page.SearchResult().selectViewFromEnrollment("Billing Transaction Interface");

        /* 5. click on cancel and original trans
         6. Click on validate button and verify no error
         NOTE: Validation should perform for Cancel first because of the order processing in Billing run,
         it should be cancel firstly to have original trans
         **/
        String kyPndSeqNumberOfCancel = querySpecifiedInfoInCreatedTrans("KY_PND_SEQ_TRANS", cancelTransId,tranID);
        String kyPndSeqNumberOfRebilled = querySpecifiedInfoInCreatedTrans("KY_PND_SEQ_TRANS", rebilledTransId,tranID);
        Page.BillingTransactionList().clickOnTransText(kyPndSeqNumberOfCancel);
        Page.BillingTransactionView().clickOnValidateButton();
        verifyBooleanResultBySoftAssert(Page.BillingTransactionView().validateTransactionIsSuccessful(),true,
                "Transaction "+ tranID +" Cancel transaction validation: ");

        //verify status code in database after validation, should be 65 as sucessful validation
        String cdTranStatusCodeOfCancel = queryStatusCodeOfCancel(kyEnroll, kyPndSeqNumberOfCancel);
       db.checkResultBySoftAssertWithMsg(cdTranStatusCodeOfCancel,"65",
               "Transaction "+ tranID + " SQL validation: Cancel's transaction status code ");

        Page.BillingTransactionView().clickOnBackBillingTransList();

        Page.BillingTransactionList().clickOnTransText(kyPndSeqNumberOfRebilled);
        Page.BillingTransactionView().clickOnValidateButton();

        verifyBooleanResultBySoftAssert(Page.BillingTransactionView().validateTransactionIsSuccessful(),true,
                "Transaction "+ tranID + " Rebilled transaction validation: ");

        Page.BillingTransactionView().clickOnBackBillingTransList();

        //verify status code in database after validation, should be 65 as sucessful validation
        String cdStatusCodeOfRebilled = queryStatusCodeOfCancel(kyEnroll,kyPndSeqNumberOfRebilled);
        db.checkResultBySoftAssertWithMsg(cdStatusCodeOfRebilled,"65",
                "Transaction "+ tranID +" SQL validation: Rebilled's transaction status code ");
    }
    private void verifyBooleanResultBySoftAssert(boolean checkedValue, boolean expectedValue, String explainMsg){
        softAssert.assertEquals(checkedValue,expectedValue, explainMsg);
    }
    private String queryInfoInProcessedTrans(String queryLabel, String tranID){
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
    private String querySpecifiedInfoInCreatedTrans(String queryLabel, String purposeCode, String tranID){
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
    private String queryStatusCodeOfCancel(String kyEnroll, String kyPndSeqNumberOfCancel){
        String queryInputCancel = "select cd_tran_status from custpro.cpm_pnd_tran_hdr where ky_enroll ="
                + kyEnroll + " and ky_pnd_seq_trans =" + kyPndSeqNumberOfCancel;
        return db.returnQueriedStringField(queryInputCancel);
    }
    private Boolean enrollMatchingFlag(String tranID){
        List<String> KYEnrollList = querySpecifiedInfoList("KY_ENROLL", tranID);
        if(KYEnrollList.size() == 2) {
            String kyEnroll0 = KYEnrollList.get(0);
            String kyEnroll1 = KYEnrollList.get(1);
            return kyEnroll0.equals(kyEnroll1);
        }
        return false;
    }
    private Boolean supplierMatchingFlag(String tranID){
        List<String> KYSupplierList = querySpecifiedInfoList("KY_SUPPLIER", tranID);
        if(KYSupplierList.size() == 2) {
            String kySupplier0 = KYSupplierList.get(0);
            String kySupplier1 = KYSupplierList.get(1);
            return kySupplier0.equals(kySupplier1);
        }
        return false;
    }
    private List<String> querySpecifiedInfoList(String label,String tranID){
        String infoList = "select "+label+" from custpro.cpm_pnd_tran_hdr NEW" +
                " where NEW.ky_enroll in(select OLD.ky_enroll from custpro.cpm_pnd_tran_hdr OLD " +
                "where OLD.ky_pnd_seq_trans =" + tranID +
                "and OLD.DT_PERIOD_START = NEW.DT_PERIOD_START " +
                "and OLD.DT_PERIOD_END = NEW.DT_PERIOD_END) " +
                "and NEW.cd_tran_status = 28";
        return db.executeQueryReturnString(infoList);
    }

}
