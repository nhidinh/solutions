package utilities.helper;

import org.openqa.selenium.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * User: Nhi Dinh
 * Date: 4/01/2019
 */
public class UploadFile {
    public void uploadByBrowseButton(String filePath, WebElement browseButton) throws AWTException {
        setClipboardData(filePath);
        browseButton.click();

        Robot robot = new Robot();
        robot.setAutoDelay(250);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void uploadByDragDrop(String filePath, WebElement target, WebDriver driver) {
        File uploadFile = new File(filePath);
        if(!uploadFile.exists()){
            throw new WebDriverException("File not found" + filePath);
        }
        int offsetX = 0;
        int offsetY = 0;
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        String inputID = ((String) executor.executeScript(JavaScriptHelper.getJsDndHelper(), target, offsetX, offsetY));
        WebElement input = driver.findElement(By.id(inputID));
        input.sendKeys(filePath);
    }

    private static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
}
