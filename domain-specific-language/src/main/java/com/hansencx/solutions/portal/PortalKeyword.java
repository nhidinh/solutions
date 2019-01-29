package com.hansencx.solutions.portal;

import com.hansencx.solutions.core.BaseKeyword;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.openqa.selenium.WebDriver;
import utilities.helper.ExcelHelper;

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
    public void callKeyword(int step, String keyword, final List<String> listParameters) {
        Map<String, Runnable> dictionary = new HashMap<String, Runnable>();
        PortalPageGenerator pageGenerator = new PortalPageGenerator(driver);

        dictionary.put("go to search page", () -> pageGenerator.TopNavigation().clickSearchButton());
        dictionary.put("navigate to portal", () -> pageGenerator.Login().goTo());
        dictionary.put("log in to portal", () -> pageGenerator.Login().logonWithUsername(listParameters.get(0), listParameters.get(1)));
        dictionary.put("search by enrollment number with filter", () -> pageGenerator.Search().searchByEnrollmentNumberWithFilter(listParameters.get(0), listParameters.get(1)));
        dictionary.put("click search", () -> pageGenerator.Search().clickSearchButton());
        dictionary.put("verify search result", () -> pageGenerator.SearchResult().verifySearchResult(listParameters.get(0)));

        //Cancel billing
        dictionary.put("execute query", () -> executeQuery(listParameters.get(0)));
        dictionary.put("check value", () -> checkValue(listParameters.get(0), listParameters.get(1)));

        if (dictionary.containsKey(keyword.toLowerCase())) {
            if (keyword.toLowerCase().equals("execute query")) {
                excelHelper.setCellValue(step, 8, databaseHelper.returnQueriedStringField(listParameters.get(0)));
                observedQueryResult = databaseHelper.returnQueriedStringField(listParameters.get(0));
//                System.out.println("query: " + listParameters.get(0));
//                System.out.println("Query result" + databaseHelper.returnQueriedStringField(listParameters.get(0)));

            } else if (keyword.toLowerCase().equals("check value")) {
                databaseHelper.checkResult(observedQueryResult, listParameters.get(1));
            }
            dictionary.get(keyword.toLowerCase()).run();
        }
    }


    private void checkValue(String observedResult, String expectedResult) {
    }
    private void executeQuery(String query) {

    }
}