package com.hansencx.solutions.portal;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.portal.pages.LoginPage;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */

public class PortalBaseTest extends BaseTest {
    public PortalPageGenerator Page;

    @BeforeMethod ()
    public void setPageGenerator(){
         Page = new PortalPageGenerator(getDriver());
     }

    @BeforeMethod(dependsOnMethods = "setPageGenerator")
    @Parameters({"username", "encodedPassword"})
    public void loginBeforeTest(String username, String encodedPassword, ITestContext testContext){
         if(!testContext.getName().contains("Login Test")){
             LoginPage loginPage = Page.Login().goTo();
             loginPage.logonWithEncodedCredential(username, encodedPassword);
         }
     }
}
