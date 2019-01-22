package utilities.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */


public class ChromeDriverManager extends DriverManager {
    @Override
    public WebDriver createDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
