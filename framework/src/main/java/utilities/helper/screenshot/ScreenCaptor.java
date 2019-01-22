package utilities.helper.screenshot;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Nhi Dinh
 * @since 1/18/2019
 */

public class ScreenCaptor {
    private static final String SCREENSHOT_EXT = "png";


    /**
     * @param driver
     * @param screenshotName
     * @param location
     * @return The Encoded String of Image
     * @throws IOException
     * @since 18/01/2019
     *
     */
    public static String takeBase64Screenshot(WebDriver driver, String screenshotName, String location) throws IOException {
        String encodedImage = null;
        FileInputStream fileInputStream = null;
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        String destination = location + screenshotName +"."+  SCREENSHOT_EXT;
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);

        try {
            fileInputStream = new FileInputStream(finalDestination);
            byte[] bytes = new byte[(int) finalDestination.length()];
            fileInputStream.read(bytes);
            encodedImage = new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

}
