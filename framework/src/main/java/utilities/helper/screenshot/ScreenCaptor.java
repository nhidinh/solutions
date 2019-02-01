package utilities.helper.screenshot;

import com.hansencx.solutions.logger.Log;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
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

    private String newFileName(){
        return System.currentTimeMillis() +"."+SCREENSHOT_EXT;
    }
    private File getNewFile(String parentFolder){
        File newFile = null;
        while (newFile == null || newFile.exists()){
            newFile = new File(parentFolder, newFileName());
        }
        return newFile;
    }

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
        String destination = location + screenshotName +"."+  SCREENSHOT_EXT;

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);

        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return encodedFile(finalDestination);
    }

    public static String encodedFile(File file){
        FileInputStream fileInputStream = null;
        String encodedFile =null;

        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            encodedFile = new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.error(e.getMessage());

        }
        return encodedFile;
    }

    public static String takeFullScreenshot(WebDriver driver, String screenshotName, String location) throws IOException {
        String destination = location + screenshotName +"."+  SCREENSHOT_EXT;

        Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        ImageIO.write(fpScreenshot.getImage(),"PNG",new File(destination));
        return encodedFile(new File(destination));
    }

}