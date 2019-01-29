package com.hansencx.portal.tests;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.database.DatabaseHelper;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;
import java.util.List;

public class CancelRebillByExcelScripting extends BaseTest {
    ExcelHelper excelHelper = new ExcelHelper();
    DatabaseHelper databaseHelper = new DatabaseHelper();
    @Test
    public void cancelBillingTest(){
        databaseHelper.createConnection("PSOLQ");
        excelHelper = new ExcelHelper();
        excelHelper.setTestDataExcelPath("D:\\Users\\nguyenv\\IdeaProjects\\solutions\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\testscripts\\CancelBillingTests.xlsx");
        excelHelper.setExcelFileSheet("Sheet1");

        PortalKeyword keyword = new PortalKeyword(getDriver(), excelHelper, databaseHelper);
        try {
            runTestScripts(keyword);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void runTestScripts(PortalKeyword keyword) throws FileNotFoundException {

        List<String> parameters;

        for(int i = 0; i<excelHelper.getLastRowNum(); i++){
            parameters = excelHelper.getRowValue(i+1);
            keyword.callKeyword(i+1,excelHelper.getCellValue(i+1, 2), parameters);
        }
    }
}
