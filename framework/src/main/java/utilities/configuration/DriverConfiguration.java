package utilities.configuration;

import com.hansencx.solutions.core.BasePage;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import utilities.configuration.driver.DriverManager;
import utilities.configuration.driver.DriverManagerFactory;
import utilities.configuration.driver.DriverType;

import java.net.MalformedURLException;
import java.net.URL;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;

/**
 * GoogleHomePage class
 *
 * @author  Huong Trinh
 * @version 1.0
 * @since   2018-12-13
 * @see BasePage
 */
public class DriverConfiguration {
    private static DriverManager driverManager;
    private static WebDriver driver;

    /**
     * Set up the driver, define target path where xxdriver will be stored.
     * @author Huong Trinh
     * @param driverType
     * @return Nothing.
     * @since   2018-12-13
     * @see
     */
    private static void manageDriver(DriverManagerType driverType) {
        WebDriverManager.getInstance(driverType).targetPath(InitialData.ROOT_DIRECTORY + "\\framework\\src\\drivers\\");
        //System.out.println(System.getProperty("user.dir") + "\\framework\\src\\drivers\\");
        WebDriverManager.getInstance(driverType).setup();
    }

    /**
     * Create object of drivers in case local machine.
     * @author Huong Trinh, Vi Nguyen
     * @param browser
     * @return WebDriver.
     * @since   2018-12-13
     * @see
     */
    public static WebDriver configureDriver(String browser){
        if (browser.toLowerCase().equals("chrome")) {
            manageDriver(CHROME);
            return new ChromeDriver();
        } else if (browser.toLowerCase().equals("firefox")) {
            manageDriver(FIREFOX);
            return new FirefoxDriver();
        }
        // Default case: Chrome
        else {
            manageDriver(CHROME);
            return new ChromeDriver();
        }

    }

    /**
     * Create object of drivers in case local machine.
     * @author Huong Trinh, Vi Nguyen
     * @param browser
     * @return WebDriver
     * @since   2018-12-13
     * @see
     */
    public static WebDriver configureRemoteDriver(String browser) throws MalformedURLException {

        URL urlHost = new URL("http://localhost:4444/wd/hub");

        if(browser.toLowerCase().equals("chrome")){
            return new RemoteWebDriver(urlHost, new ChromeOptions());
        }else if(browser.toLowerCase().equals("firefox")){
            return new RemoteWebDriver(urlHost, new FirefoxOptions());
        }
        //Default case: Chrome
        return new RemoteWebDriver(urlHost, new ChromeOptions());
    }
}
