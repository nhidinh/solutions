package com.hansencx.portal.tests;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;

public class CancelRebillByExcelScripting extends BaseTest {
    ExcelHelper excelHelper;
    DatabaseHelper databaseHelper = new DatabaseHelper();

    @Test
    public void cancelBillingTest() throws FileNotFoundException {
        databaseHelper.createConnection("PSOLQ");
        excelHelper = new ExcelHelper("D:\\Users\\nguyenv\\IdeaProjects\\solutions\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\testscripts\\CancelBillingTests.xlsx","Sheet1");

        PortalKeyword keyword = new PortalKeyword(getDriver(), excelHelper, databaseHelper);
        try {
            keyword.runTestScriptsTest();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
