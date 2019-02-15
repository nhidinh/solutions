package com.hansencx.portal.tests.core;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.portal.pages.LoginPage;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.seleniumhq.jetty9.util.security.Credential;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.helper.PropertiesHelper;

import java.io.IOException;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */

public class PortalBaseTest extends BaseTest {
    protected PortalPageGenerator Page;
    protected PropertiesHelper Credential;

    @BeforeMethod ()
    public void setPageGenerator(){
         Page = new PortalPageGenerator(getDriver());
     }
     @BeforeMethod
     public void setCredentialData() throws IOException {
        Credential = new PropertiesHelper("credentials.properties");
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
