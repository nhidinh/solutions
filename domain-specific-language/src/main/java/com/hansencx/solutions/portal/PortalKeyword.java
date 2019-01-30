package com.hansencx.solutions.portal;

import com.hansencx.solutions.core.BaseKeyword;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.WebDriver;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PortalKeyword class
 *
 * @author  Vi Nguyen, Huong Trinh
 * @version 1.0
 * @since   2018-12-03
 * @see BaseKeyword
 *
 */
public class PortalKeyword extends BaseKeyword {

    private ExcelHelper excelHelper;
    private DatabaseHelper databaseHelper;

    private static String observedQueryResult = "";

    /**
     * Constructors
     */
    public PortalKeyword(WebDriver driver, ExcelHelper excelHelper) {
        super(driver);
        this.excelHelper = excelHelper;
    }

    public PortalKeyword(WebDriver driver, ExcelHelper excelHelper, DatabaseHelper databaseHelper) {
        super(driver);
        this.excelHelper = excelHelper;
        this.databaseHelper = databaseHelper;
    }

    /**
     * Do mapping and call mapped keywords.
     * @author Huong Trinh
     * @param
     * @return ResultSet.
     * @since   2019-01-22
     * @see
     */

    public void callKeyword(int step, String keyword, final List<XSSFCell> listParameters) {
        Map<String, Runnable> dictionary = new HashMap<String, Runnable>();
        PortalPageGenerator pageGenerator = new PortalPageGenerator(driver);
        FormulaEvaluator evaluator = excelHelper.getExcelWorkBook().getCreationHelper().createFormulaEvaluator();

        dictionary.put("go to search page", () -> pageGenerator.TopNavigation().clickSearchButton());
        dictionary.put("navigate to portal", () -> pageGenerator.Login().goTo());
        dictionary.put("log in to portal", () -> pageGenerator.Login().logonWithUsername(excelHelper.getCellValue(listParameters.get(0)), excelHelper.getCellValue(listParameters.get(1))));
        dictionary.put("search by enrollment number with filter", () -> pageGenerator.Search().searchByEnrollmentNumberWithFilter(excelHelper.getCellValue(listParameters.get(0)), excelHelper.getCellValue(listParameters.get(1))));
        dictionary.put("click search", () -> pageGenerator.Search().clickSearchButton());
        dictionary.put("verify search result", () -> pageGenerator.SearchResult().verifySearchResult(listParameters.get(0).getRawValue()));

        //Cancel billing
        dictionary.put("execute query", () -> executeQuery(""));
        dictionary.put("check value", () ->  databaseHelper.checkResult(evaluator.evaluate(listParameters.get(0)).getStringValue(),excelHelper.getCellValue(listParameters.get(1))));

        if (dictionary.containsKey(keyword.toLowerCase())) {
            if (keyword.toLowerCase().equals("execute query")) {
                excelHelper.setCellData(databaseHelper.returnQueriedStringField(evaluator.evaluate(listParameters.get(0)).getStringValue()), step, 8);
            }
            dictionary.get(keyword.toLowerCase()).run();
        }
    }

    public void runTestScriptsTest() throws FileNotFoundException {
        List<XSSFCell> listCell;

        for (int i = 0; i < excelHelper.getLastRowNum(); i++) {
            //excelHelper.openFile();
            listCell = excelHelper.getRow(i + 1);
            callKeyword(i + 1, excelHelper.getCellValue(i + 1, 2), listCell);
        }
    }

    /**
     * This is a fake method
     * @param query
     */
    private void executeQuery(String query) {

    }
}