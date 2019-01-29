package com.hansencx.portal.tests;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;
import java.util.List;

public class SearchTestsByExcelScripting extends BaseTest {

    ExcelHelper excelHelper;

    @Test
    public void searchTest(){
        excelHelper = new ExcelHelper();
        excelHelper.setTestDataExcelPath("D:\\Users\\"+System.getProperty("user.name")+"\\SearchTests.xlsx");
        excelHelper.setExcelFileSheet("Sheet1");

        PortalKeyword keyword = new PortalKeyword(getDriver(), excelHelper);
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
