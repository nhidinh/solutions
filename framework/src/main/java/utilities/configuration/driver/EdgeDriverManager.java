package utilities.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */


public class EdgeDriverManager extends DriverManager {
    @Override
    public WebDriver createDriver(){
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }
}
