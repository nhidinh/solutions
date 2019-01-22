package utilities.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */
public class FirefoxDriverManager extends DriverManager {
    @Override
    public WebDriver createDriver(){
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }
}