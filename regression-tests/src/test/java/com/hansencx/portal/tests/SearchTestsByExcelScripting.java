package com.hansencx.portal.tests;

import com.hansencx.solutions.core.BaseTest;
import com.hansencx.solutions.portal.PortalKeyword;
import org.testng.annotations.Test;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;
import java.util.List;

public class SearchTestsByExcelScripting extends BaseTest {

    @Test
    public void searchTest(){
        PortalKeyword keyword = new PortalKeyword(getDriver());
        try {
            runTestScripts("D:\\Users\\nguyenv\\SearchTests.xlsx","Sheet1", keyword);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void runTestScripts(String filePath, String sheetName, PortalKeyword keyword) throws FileNotFoundException {
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.setTestDataExcelPath(filePath);
        excelHelper.setExcelFileSheet(sheetName);

        List<String> parameters;

        for(int i = 0; i<excelHelper.getLastRowNum(); i++){
            System.out.println(excelHelper.getCellData(0,0));
            parameters = excelHelper.getRowValue(i+1);
            System.out.println("step: " + excelHelper.getCellValue(i+1, 2));
            keyword.callKeyword(excelHelper.getCellValue(i+1, 2), parameters);
            System.out.println("----");

        }

    }
}
