package com.hansencx.portal.tests.core;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.logger.Log;
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
    protected static PortalPageGenerator Page;
    protected PropertiesHelper Credential;

    //CREDENTIAL DATA
    protected static String qageneric_username;
    protected static String qageneric_password;

    protected static String qaapprover_username;
    protected static String qaapprover_password;

    protected static String qarequester_username;
    protected static String qarequester_password;

    @BeforeMethod ()
    public void setPageGenerator(){
         Page = new PortalPageGenerator(getDriver());
     }
     @BeforeMethod
     public void setCredentialData() throws IOException {
        Credential = new PropertiesHelper("credentials.properties");
         //CREDENTIAL DATA
         qageneric_username = Credential.getPropertyValue("qageneric_username");
         qageneric_password = Credential.getPropertyValue("qageneric_password");

         qaapprover_username = Credential.getPropertyValue("qaapprover_username");
         qaapprover_password = Credential.getPropertyValue("qaapprover_password");

         qarequester_username = Credential.getPropertyValue("qarequester_username");
         qarequester_password = Credential.getPropertyValue("qarequester_password");
     }

    @BeforeMethod(dependsOnMethods = "setPageGenerator")
    @Parameters({"username", "encodedPassword"})
    public void loginBeforeTest(String username, String encodedPassword, ITestContext testContext){
        Log.info("Perform Login action before test");
         if(!testContext.getName().contains("Login Test")){
             LoginPage loginPage = Page.Login().goTo();
             loginPage.logonWithEncodedCredential(username, encodedPassword);
         }
     }
}
