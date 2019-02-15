package com.hansencx.solutions.core;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.WebDriver;
import utilities.helper.ExcelHelper;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * BaseKeyword class
 *
 * @author  Vi Nguyen
 * @version 1.0
 * @since   2018-12-03
 */
public class BaseKeyword {
    protected static final int FIRST_OUTPUR_ARGUMENT_INDEX = 8;
    private ExcelHelper excelHelper;
    private WebDriver driver;

    /**
     * Constructor
     */
    public BaseKeyword(WebDriver driver) {
        this.setDriver(driver);
    }

    public BaseKeyword(WebDriver driver, ExcelHelper excelHelper) {
        this.setDriver(driver);
        this.setExcelHelper(excelHelper);
    }

    /**
     * Getters and Setters
     *
     * @return
     */
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public ExcelHelper getExcelHelper() {
        return excelHelper;
    }

    public void setExcelHelper(ExcelHelper excelHelper) {
        this.excelHelper = excelHelper;
    }

    /**
     * Do mapping and call mapped keywords.
     *
     * @param step, keyword, listParameters
     * @return Nothing.
     * @author Vi Nguyen
     * @see
     * @since 2019-01-22
     */
    protected void callKeyword(int step, String keyword, final List<XSSFCell> listParameters) {
    }

    /**
     * run test scripts from excel file
     *
     * @param
     * @return Nothing.
     * @author Vi Nguyen
     * @see
     * @since 2019-01-22
     */
    public void runTestScripts() throws FileNotFoundException {
        List<XSSFCell> listCell;

        for (int i = 0; i < getExcelHelper().getLastRowNum(); i++) {
            listCell = getExcelHelper().getExcelScriptsArguments(i + 1);
            callKeyword(i + 1, getExcelHelper().getCellValue(i + 1, 2), listCell);
        }
    }

    protected void setReturnedValueToCell(int row, int col, String value) {
        getExcelHelper().setCellValue(row, col, value);
    }


}
