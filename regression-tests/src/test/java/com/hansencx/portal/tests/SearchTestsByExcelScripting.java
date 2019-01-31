package com.hansencx.portal.tests;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.configuration.InitialData;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;

public class SearchTestsByExcelScripting extends BaseTest {

    ExcelHelper excelHelper;

    @Test
    public void searchTest() {
        try {
            excelHelper = new ExcelHelper(InitialData.PARENT_DIR +"\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\testscripts\\SearchTests.xlsx", "Sheet1");

            PortalKeyword keyword = new PortalKeyword(getDriver(), excelHelper);
            keyword.runTestScriptsTest();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
