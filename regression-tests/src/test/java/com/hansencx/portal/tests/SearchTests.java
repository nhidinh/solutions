package com.hansencx.portal.tests;

import com.hansencx.portal.common.DataFilePathHandler;
import com.hansencx.portal.tests.core.PortalBaseTest;
import com.hansencx.solutions.logger.Log;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.BaseTestMethod;
import utilities.helper.ExcelHelper;
import utilities.helper.SoftAssert;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */

public class SearchTests extends PortalBaseTest {
    private ExcelHelper excelHelper;
    private ExcelHelper excelResult;
    private ArrayList<String> listOfTestCase = new ArrayList<String>();
    private String testCaseName;
    private int testCaseIndex = 0;
    private SoftAssert softAssert;


    @BeforeTest
    public void setUpTestData() throws FileNotFoundException {
        excelHelper = new ExcelHelper(DataFilePathHandler.PORTAL_DATA_TEST_PATH, DataFilePathHandler.PORTAL_DATA_SHEET_NAME);
        excelResult = new ExcelHelper(DataFilePathHandler.PORTAL_DATA_TEST_RESULT_PATH, DataFilePathHandler.PORTAL_DATA_TEST_RESULT_SHEET_NAME);
    }

    @DataProvider
    public Object[][] getData() {
        Object[][] data = excelHelper.getTableArray();
        int numberOfRow = excelHelper.getNumberOfRow();
        for (int row = 0; row < numberOfRow - 1; row++) {
            listOfTestCase.add(data[row][0].toString());
        }
        return data;
    }

    @BeforeMethod(alwaysRun = true)
    private void setTestCaseName(ITestResult result, Method method) {
        testCaseName = listOfTestCase.get(testCaseIndex);
        testCaseIndex = testCaseIndex + 1;

        try {
            BaseTestMethod baseTestMethod = (BaseTestMethod) result.getMethod();
            Field f = baseTestMethod.getClass().getSuperclass().getDeclaredField("m_description");
            f.setAccessible(true);
            f.set(baseTestMethod, testCaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        softAssert = new SoftAssert(getDriver(), testCaseName);
    }

    @Test(description = "Search by Enrollment Number with Data File", dataProvider = "getData")
    public void searchByEnrollmentNumberWithDataFile(String testcaseName, String filterOption, String enrollmentNumberValue, String result) {
        int resultValue = Integer.parseInt(result);

        Page.TopNavigation().clickSearchButton();
        if (filterOption.equals("contains")) {
            Page.Search().selectSupplierByName("Talen Energy Electric");
        }
        Page.Search().searchByEnrollmentNumberWithFilter(filterOption, enrollmentNumberValue);
        Page.Search().clickSearchButton();
        int numberOfResult = Page.SearchResult().getNumberOfResult();
        softAssert.assertEquals(numberOfResult, resultValue);
        softAssert.assertAll();
        Log.info("Complete Test case: " + testcaseName);
        System.out.println("Compete Test case: " + testcaseName);
    }
}
