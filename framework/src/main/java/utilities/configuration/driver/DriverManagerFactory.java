package utilities.configuration.driver;

import io.github.bonigarcia.wdm.DriverManagerType;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/17/2019
 */


public class DriverManagerFactory {
    public static DriverManager getManager(DriverType type){
        DriverManager driverManager;
        switch (type){
            default:
                driverManager = new ChromeDriverManager();
                break;
            case firefox:
                driverManager = new FirefoxDriverManager();
                break;
            case ie:
                driverManager = new IEDriverManager();
                break;
            case edge:
                driverManager = new EdgeDriverManager();
                break;

        }
        return driverManager;
    }
}
