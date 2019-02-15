package com.hansencx.portal.tests.excelscripting;

import com.hansencx.portal.common.DataFilePathHandler;
import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;

public class CancelRebillByExcelScripting extends BaseTest {

    @Test
    public void cancelBillingTest() throws FileNotFoundException {
        ExcelHelper excelHelper;
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.createConnection("PSOLQ");
        excelHelper = new ExcelHelper(DataFilePathHandler.SCRIPTS_DIRECTORY_PATH + "\\CancelBillingTests_.xlsx","Sheet1");

        PortalKeyword keyword = new PortalKeyword(getDriver(), excelHelper, databaseHelper);
        try {
            keyword.runTestScripts();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
