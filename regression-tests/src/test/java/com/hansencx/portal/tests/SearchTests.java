package com.hansencx.portal.tests;

import com.hansencx.portal.common.DataFilePathHandler;
import com.hansencx.solutions.logger.Log;
import com.hansencx.solutions.portal.PortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.configuration.InitialData;
import utilities.helper.ExcelHelper;
import utilities.helper.FailureHandling;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class SearchTests extends PortalBaseTest {
    private ExcelHelper excelHelper;

    @Test(description = "Search by Enrollment Number With Filter 'in list' ")
    public void searchByEnrollmentNumberInList(){
        Page.TopNavigation().clickSearchButton();
        Page.Search().searchByEnrollmentNumberWithFilter("in list", "1");
        Page.Search().clickSearchButton();
        Assert.assertEquals(Page.SearchResult().getNumberOfResult(),2 );
    }

    @BeforeTest
    public void setUpTestData() throws FileNotFoundException {
        excelHelper = new ExcelHelper(DataFilePathHandler.PORTAL_DATA_TEST_PATH, DataFilePathHandler.PORTAL_DATA_SHEET_NAME);
    }

    @Test(description = "Search by Enrollment Number with Data File")
    public void searchByEnrollmentNumberWithDataFile(){
        int countRow = excelHelper.getNumberOfRow();
        String testcaseName;
        String filterOption;
        String enrollmentNumberValue;
        String result;
        String executedTime;
        int filterOptionCell = excelHelper.getCellIndexByText("Filter");
        int EnrollmentNumberValueCell = excelHelper.getCellIndexByText("Value");
        int tcNameCell = excelHelper.getCellIndexByText("TestCaseName");
        int resultCell = excelHelper.getCellIndexByText("Result");
        int statusCell = excelHelper.getCellIndexByText("Status");
        int executedTimeCell = excelHelper.getCellIndexByText("ExecutedTime");
        int messageCell = excelHelper.getCellIndexByText("Message");

        for(int i = 1; i<countRow; i++){
            filterOption = excelHelper.getCellData(i, filterOptionCell);
            enrollmentNumberValue = excelHelper.getCellData(i, EnrollmentNumberValueCell);
            testcaseName = excelHelper.getCellData(i, tcNameCell);
            result = excelHelper.getCellData(i, resultCell);
            int resultValue = Integer.parseInt(result);

            Page.TopNavigation().clickSearchButton();
            if(filterOption.equals("contains")){
                Page.Search().selectSupplierByName("Talen Energy Electric");
            }
            Page.Search().searchByEnrollmentNumberWithFilter(filterOption, enrollmentNumberValue);
            Page.Search().clickSearchButton();
            int numberOfResult = Page.SearchResult().getNumberOfResult();
            executedTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

            excelHelper.setCellValue(i, executedTimeCell, executedTime);
            try {
                Assert.assertEquals(numberOfResult, resultValue);
                excelHelper.setCellValue(i, statusCell, "PASS");
            }catch (AssertionError e){
                excelHelper.setCellValue(i, statusCell, "FAILED");
                excelHelper.setCellValue(i, messageCell, e.getMessage());
                FailureHandling.continueAtFailedTestCase(e, testcaseName);
            }
            Log.info("Complete Test case: "+ testcaseName);
            System.out.println("Compete Test case: " + testcaseName);
        }
    }

}
