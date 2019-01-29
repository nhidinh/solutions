package com.hansencx.solutions.portal;

import com.hansencx.solutions.core.BaseKeyword;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.openqa.selenium.WebDriver;
import utilities.helper.ExcelHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author Vi Nguyen
 * @return
 * @since 2019-01-22
 */
public class PortalKeyword extends BaseKeyword {

    ExcelHelper excelHelper;
    DatabaseHelper databaseHelper = new DatabaseHelper();

    public PortalKeyword(WebDriver driver, ExcelHelper excelHelper) {
        super(driver);
        this.excelHelper = excelHelper;
    }

    public void callKeyword(int step, String keyword, final List<String> listParameters) {
        Map<String, Runnable> dictionary = new HashMap<String, Runnable>();
        PortalPageGenerator pageGenerator = new PortalPageGenerator(driver);

        dictionary.put("go to search page", ()-> pageGenerator.TopNavigation().clickSearchButton());
        dictionary.put("navigate to portal", () -> pageGenerator.Login().goTo());
        dictionary.put("log in to portal", () -> pageGenerator.Login().logonWithUsername(listParameters.get(0),listParameters.get(1)));
        dictionary.put("search by enrollment number with filter", () -> pageGenerator.Search().searchByEnrollmentNumberWithFilter(listParameters.get(0),listParameters.get(1)));
        dictionary.put("click search", () -> pageGenerator.Search().clickSearchButton());
        dictionary.put("verify search result", ()-> pageGenerator.SearchResult().verifySearchResult(listParameters.get(0)));

        //Cancel billing
        //dictionary.put("");

        dictionary.put("execute query", () -> executeQuery(listParameters.get(0)));
        dictionary.put("check value", () -> databaseHelper.checkResult(listParameters.get(0),listParameters.get(1)));

        if (dictionary.containsKey(keyword.toLowerCase())){
            ///dictionary.get(keyword.toLowerCase()).run();
            if(keyword.toLowerCase().equals("execute query")) {
                System.out.println("this is execute query");
                //workbookHelper.setCellValue(step, 9, list.executeOracleQuery(listPara.get(0)));
                //excelHelper.setCellValue(step, 9, list.executeOracleQuery(listParameters.get(0)));
                excelHelper.setCellValue(step, 9, databaseHelper.returnQueriedStringField(listParameters.get(0)));
                System.out.println("WIN" + excelHelper.getCellValue(step, 9));
            }
            dictionary.get(keyword.toLowerCase()).run();
        }
    }

    private void executeQuery(String query){

    }
}