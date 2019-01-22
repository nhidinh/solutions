package utilities.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * JavaScriptHelper class (IN DEVELOPING...)
 *
 * @author  Nhi Dinh
 * @version 1.0
 * @since 04/01/2019
 */
public class JavaScriptHelper {
    public static void executeJSWithElement(String javascript, WebElement element, WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript(javascript, element);
    }

    public static void executeJS(String javascript, WebDriver driver){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript(javascript);
    }
    public static String getJsDndHelper() {
        return "var target = arguments[0]," +
                "offsetX = arguments[1]," +
                "offsetY = arguments[2]," +
                "document = target.ownerDocument || document," +
                "window = document.defaultView || window;" +
                "" +
                "var input = document.createElement('INPUT');" +
                "input.type = 'file';" +
                "input.style.display = 'none';" +
                "input.id = 'jsInput';"+
                "input.onchange = function () {" +
                "  target.scrollIntoView(true);" +
                "  var rect = target.getBoundingClientRect()," +
                "  x = rect.left + (offsetX || (rect.width >> 1))," +
                "  y = rect.top + (offsetY || (rect.height >> 1))," +
                "  dataTransfer = { files: this.files };" +
                "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
                "var evt = document.createEvent('MouseEvent');" +
                "evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
                "evt.dataTransfer = dataTransfer;" +
                "target.dispatchEvent(evt);" +
                "  });" +
                "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
                "};" +
                "document.body.appendChild(input);"+
                "return input.id";
    }
}
