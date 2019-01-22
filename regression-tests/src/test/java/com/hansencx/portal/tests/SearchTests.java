package com.hansencx.portal.tests;

import com.hansencx.solutions.portal.PortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.hansencx.solutions.logger.Log;
import utilities.helper.ExcelHelper;
import utilities.helper.FailureHandling;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class SearchTests extends PortalBaseTest {
    @Test(description = "Search by Enrollment Number With Filter 'in list' ")
    public void searchByEnrollmentNumberInList(){
        Page.TopNavigation().clickSearchButton();
        Page.Search().searchByEnrollmentNumberWithFilter("in list", "1");
        Page.Search().clickSearchButton();
        Assert.assertEquals(Page.SearchResult().getNumberOfResult(),2 );
    }

    @BeforeTest
    public void setUpTestData(){
        String DataDirectory = "D:\\Users\\dinhn\\IdeaProjects\\solutions\\solutions\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\datatest\\";
        String DataFileName = "PortalDataTest.xlsx";
        String SheetName = "EnrollmentNumber";
        ExcelHelper.setupExcelTestData(DataDirectory, DataFileName, SheetName);
    }

    @Test(description = "Search by Enrollment Number with Data File")
    public void searchByEnrollmentNumberWithDataFile(){
        int countRow = ExcelHelper.getNumberOfRow();
        String testcaseName;
        String filterOption;
        String enrollmentNumberValue;
        String result;
        int filterOptionCell = ExcelHelper.getCellIndexByText("Filter");
        int EnrollmentNumberValueCell = ExcelHelper.getCellIndexByText("Value");
        int tcNameCell = ExcelHelper.getCellIndexByText("TestCaseName");
        int resultCell = ExcelHelper.getCellIndexByText("Result");

        for(int i = 1; i<countRow; i++){
            filterOption = ExcelHelper.getCellData(i, filterOptionCell);
            enrollmentNumberValue = ExcelHelper.getCellData(i, EnrollmentNumberValueCell);
            testcaseName = ExcelHelper.getCellData(i, tcNameCell);
            result = ExcelHelper.getCellData(i, resultCell);
            int resultValue = Integer.parseInt(result);

            Page.TopNavigation().clickSearchButton();
            if(filterOption.equals("contains")){
                Page.Search().selectSupplierByName("Talen Energy Electric");
            }
            Page.Search().searchByEnrollmentNumberWithFilter(filterOption, enrollmentNumberValue);
            Page.Search().clickSearchButton();
            int numberOfResult = Page.SearchResult().getNumberOfResult();
            try {
                Assert.assertEquals(numberOfResult, resultValue);
            }catch (AssertionError e){
                FailureHandling.continueAtFailedTestCase(e, testcaseName);
            }
            Log.info("Complete Test case: "+ testcaseName);
            System.out.println("Compete Test case: " + testcaseName);

        }
    }
}
