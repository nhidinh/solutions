package utilities.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */

public class IEDriverManager extends  DriverManager {
    @Override
    public WebDriver createDriver(){
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver();
    }
}