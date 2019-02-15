package com.hansencx.solutions.portal;

import com.hansencx.solutions.core.BaseKeyword;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.utilities.PortalPageGenerator;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.WebDriver;
import utilities.helper.ExcelHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PortalKeyword class
 *
 * @author Vi Nguyen
 * @version 1.0
 * @see BaseKeyword
 * @since 2019-1-30
 */
public class PortalKeyword extends BaseKeyword {

    private DatabaseHelper databaseHelper;

    /**
     * Constructors
     */
    public PortalKeyword(WebDriver driver, ExcelHelper excelHelper) {
        super(driver, excelHelper);
    }

    public PortalKeyword(WebDriver driver, ExcelHelper excelHelper, DatabaseHelper databaseHelper) {
        super(driver, excelHelper);
        this.databaseHelper = databaseHelper;
    }

    /**
     * Do mapping and call mapped keywords.
     *
     * @param step, keyword, listParameters
     * @return Nothing.
     * @author Vi Nguyen
     * @see
     * @since 2019-01-22
     */

    public void callKeyword(int step, String keyword, final List<XSSFCell> listParameters) {
        Map<String, Runnable> dictionary = new HashMap<String, Runnable>();
        PortalPageGenerator pageGenerator = new PortalPageGenerator(getDriver());
        FormulaEvaluator evaluator = getExcelHelper().getExcelWorkBook().getCreationHelper().createFormulaEvaluator();

        // Search tests
        dictionary.put("go to search page", () -> pageGenerator.TopNavigation().clickSearchButton());
        dictionary.put("navigate to portal", () -> pageGenerator.Login().goTo());
        dictionary.put("log in to portal", () -> pageGenerator.Login().logonWithUsername(getExcelHelper().getCellValue(listParameters.get(0)), getExcelHelper().getCellValue(listParameters.get(1))));
        dictionary.put("search by enrollment number with filter", () -> pageGenerator.Search().searchByEnrollmentNumberWithFilter(getExcelHelper().getCellValue(listParameters.get(0)), getExcelHelper().getCellValue(listParameters.get(1))));
        dictionary.put("click search", () -> pageGenerator.Search().clickSearchButton());
        dictionary.put("verify search result", () -> pageGenerator.SearchResult().verifySearchResult(listParameters.get(0).getRawValue()));

        // Cancel billing
        dictionary.put("navigate to service center update secion", () -> pageGenerator.LeftNavigation().clickServiceCenterUpdateMenu());
        dictionary.put("service update - click add new", () -> pageGenerator.ServiceCenterUpdate().clickAddNewButton());
        dictionary.put("service update add new - scroll to custpro aread", () -> pageGenerator.AddServiceCenterUpdate().scrollToCustproArea());
        dictionary.put("service update add new - select cancel rebill checkbox", () -> pageGenerator.AddServiceCenterUpdate().checkCreateCancelRebillCheckbox());
        dictionary.put("service update add new - click import button", () -> pageGenerator.AddServiceCenterUpdate().clickImportButton());

        dictionary.put("import service update - upload cancel rebill file", () -> pageGenerator.ImportServiceCenterUpdate().uploadCancelRebillFile(getExcelHelper().getCellValue(listParameters.get(0))));
        dictionary.put("import service update - cick upload button", () -> pageGenerator.ImportServiceCenterUpdate().clickUploadButton());

        dictionary.put("create cancel rebill - verify upload successfully", () -> pageGenerator.CreateCancelRebill().verifyUploadSuccessfullyWithNoError());
        dictionary.put("create cancel rebill - get list of uploaded transactions", () -> cancelRebillGetListTransactions());
        dictionary.put("create cancel rebill - click process button", () -> pageGenerator.CreateCancelRebill().clickProcessButton());
        dictionary.put("create cancel rebill - do process", () -> pageGenerator.EnterReasonForProcess().doProcess(getExcelHelper().getCellValue(listParameters.get(0)), getExcelHelper().getCellValue(listParameters.get(1))));
        dictionary.put("create cancel rebill - get created time", () -> getCacelRebillCreatedTime());

        dictionary.put("messsage - wait for message dissmisses", () -> pageGenerator.WaitMessageDialog().waitForMessageDismiss());
        dictionary.put("dialog - wait for popup message box", () -> pageGenerator.PortalDialog().waitForPopupMessageBox());

        //Cancel billing
        dictionary.put("execute query", () -> executeQuery(""));
        dictionary.put("check value", () -> databaseHelper.checkResultByAssert(evaluator.evaluate(listParameters.get(0)).getStringValue(), getExcelHelper().getCellValue(listParameters.get(1))));

        if (dictionary.containsKey(keyword.toLowerCase())) {
            if (keyword.toLowerCase().equals("execute query")) {
                setReturnedValueToCell(step, FIRST_OUTPUR_ARGUMENT_INDEX, databaseHelper.returnQueriedStringField(evaluator.evaluate(listParameters.get(0)).getStringValue()));
            } else if (keyword.toLowerCase().equals("create cancel rebill")) {
                setReturnedValueToCell(step, FIRST_OUTPUR_ARGUMENT_INDEX, pageGenerator.CreateCancelRebill().getListTransactionID());
            } else if (keyword.toLowerCase().equals("create cancel rebill - get created time")) {
                setReturnedValueToCell(step, FIRST_OUTPUR_ARGUMENT_INDEX, pageGenerator.CreateCancelRebill().getListTransactionID());
            }
            dictionary.get(keyword.toLowerCase()).run();
        }
    }

    /**
     * This is a fake method
     *
     * @param query
     */
    private void executeQuery(String query) {

    }

    private void cancelRebillGetListTransactions() {}

    private void getCacelRebillCreatedTime() {}
}