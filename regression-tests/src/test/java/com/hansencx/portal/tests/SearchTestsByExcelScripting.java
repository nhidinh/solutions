package com.hansencx.portal.tests;

import com.hansencx.portal.common.DataFilePathHandler;
import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.helper.ExcelHelper;

public class SearchTestsByExcelScripting extends BaseTest {

    @Test
    public void searchTest() {
        try {
            ExcelHelper excelHelper = new ExcelHelper(DataFilePathHandler.SCRIPTS_DIRECTORY_PATH + "SearchTests.xlsx", "Sheet1");

            PortalKeyword keyword = new PortalKeyword(getDriver(), excelHelper);
            keyword.runTestScripts();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
