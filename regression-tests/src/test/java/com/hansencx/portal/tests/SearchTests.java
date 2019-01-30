package com.hansencx.portal.tests;

import com.hansencx.solutions.portal.PortalBaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.hansencx.solutions.logger.Log;
import utilities.configuration.InitialData;
import utilities.helper.ExcelHelper;
import utilities.helper.FailureHandling;
import utilities.helper.StringEncrypt;

import java.io.FileNotFoundException;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class SearchTests extends PortalBaseTest {
    ExcelHelper excelHelper;// = new ExcelHelper();

    @Test(description = "Search by Enrollment Number With Filter 'in list' ")
    public void searchByEnrollmentNumberInList(){
        Page.TopNavigation().clickSearchButton();
        Page.Search().searchByEnrollmentNumberWithFilter("in list", "1");
        Page.Search().clickSearchButton();
        Assert.assertEquals(Page.SearchResult().getNumberOfResult(),2 );
    }

    @BeforeTest
    public void setUpTestData() throws FileNotFoundException {
        String DataDirectory = InitialData.PARENT_DIR +"\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\datatest\\";
        String DataFileName = "PortalDataTest.xlsx";
        String SheetName = "EnrollmentNumber";
        excelHelper = new ExcelHelper(DataDirectory + DataFileName,SheetName);
        //excelHelper.setupExcelTestData(DataDirectory, DataFileName, SheetName);
    }

    @Test(description = "Search by Enrollment Number with Data File")
    public void searchByEnrollmentNumberWithDataFile(){
        int countRow = excelHelper.getNumberOfRow();
        String testcaseName;
        String filterOption;
        String enrollmentNumberValue;
        String result;
        int filterOptionCell = excelHelper.getCellIndexByText("Filter");
        int EnrollmentNumberValueCell = excelHelper.getCellIndexByText("Value");
        int tcNameCell = excelHelper.getCellIndexByText("TestCaseName");
        int resultCell = excelHelper.getCellIndexByText("Result");

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
