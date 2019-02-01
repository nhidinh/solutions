package com.hansencx.solutions.portal.utilities;

import com.hansencx.solutions.core.BasePage;
import com.hansencx.solutions.portal.pages.*;
import com.hansencx.solutions.portal.pages.navigation.LeftNavigation;
import com.hansencx.solutions.portal.pages.navigation.TopNavigation;
import com.hansencx.solutions.portal.pages.servicecenter.approvals.EnterReasonForApprovalDialog;
import com.hansencx.solutions.portal.pages.servicecenter.approvals.ServiceCenterApprovalDetailsPage;
import com.hansencx.solutions.portal.pages.servicecenter.approvals.ServiceCenterApprovalsPage;
import com.hansencx.solutions.portal.pages.servicecenter.history.ServiceCenterHistoryDetailsPage;
import com.hansencx.solutions.portal.pages.servicecenter.history.ServiceCenterHistoryPage;
import com.hansencx.solutions.portal.pages.servicecenter.update.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */

public class PortalPageGenerator {
    public WebDriver driver;

    public PortalPageGenerator(WebDriver driver){
        this.driver = driver;
    }

    private <TPage extends BasePage> TPage GetPage(Class<TPage> pageClass){
        return PageFactory.initElements(driver, pageClass);
    }

    ////GETTING PAGES:
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

    /////// GETTING SERVICE CENTER UPDATE PAGES:
    public ServiceCenterUpdatePage ServiceCenterUpdate(){
        return GetPage(ServiceCenterUpdatePage.class);
    }
    public AddServiceCenterUpdatePage AddServiceCenterUpdate(){
        return GetPage(AddServiceCenterUpdatePage.class);
    }
    public CreateCancelRebillPage CreateCancelRebill(){
        return GetPage(CreateCancelRebillPage.class);
    }
    public EnterReasonForProcessDialog EnterReasonForProcess(){
        return GetPage(EnterReasonForProcessDialog.class);
    }
    public ImportServiceCenterUpdatePage ImportServiceCenterUpdate(){
        return GetPage(ImportServiceCenterUpdatePage.class);
    }
    public PortalDialog PortalDialog(){
        return GetPage(PortalDialog.class);
    }
    public WaitMessageDialog WaitMessageDialog(){
        return GetPage(WaitMessageDialog.class);
    }
    /////// GETTING SERVICE CENTER HISTORY PAGES:
    public ServiceCenterHistoryPage ServiceCenterHistory(){
        return GetPage(ServiceCenterHistoryPage.class);
    }
    public ServiceCenterHistoryDetailsPage ServiceCenterHistoryDetails(){
        return GetPage(ServiceCenterHistoryDetailsPage.class);
    }

    ////// GETTING SERVICE CENTER APPROVAL PAGES:
    public ServiceCenterApprovalsPage ServiceCenterApprovals(){
        return GetPage(ServiceCenterApprovalsPage.class);
    }
    public ServiceCenterApprovalDetailsPage ServiceCenterApprovalDetails(){
        return GetPage(ServiceCenterApprovalDetailsPage.class);
    }
    public EnterReasonForApprovalDialog EnterReasonForApprovalDialog(){
        return GetPage(EnterReasonForApprovalDialog.class);
    }

    ////GETTING NAVIGATION
    public TopNavigation TopNavigation(){
        return GetPage(TopNavigation.class);
    }
    public LeftNavigation LeftNavigation(){
        return GetPage(LeftNavigation.class);
    }

    //// GETTING PAGE: BIILING TRANSACTION LIST PAGE
    public BillingTransactionListPage BillingTransactionList(){
        return GetPage(BillingTransactionListPage.class);
    }
}
