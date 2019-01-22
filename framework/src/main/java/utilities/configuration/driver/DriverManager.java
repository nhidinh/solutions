package utilities.configuration.driver;

import org.openqa.selenium.WebDriver;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */


public abstract class DriverManager {
    protected WebDriver driver;
    protected abstract WebDriver createDriver();

    public WebDriver getDriver(){
        if(driver == null){
            driver = createDriver();
        }
        return driver;
    }
}