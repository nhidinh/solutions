package utilities.helper;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;
import com.hansencx.solutions.logger.Log;
import org.apache.poi.ss.usermodel.Cell;
import utilities.configuration.InitialData;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 */


public class ExcelHelper {

    private static Platform platform = InitialData.PLATFORM;
    private static String testDataExcelPath;

    private static XSSFWorkbook excelWorkBook;
    private static XSSFSheet excelSheet;
    private static XSSFCell cell;
    private static XSSFRow row;

    private static int rowNumber;
    private static int columnNumber;

    public static int getRowNumber() {
        return rowNumber;
    }

    public static void setRowNumber(int rowNumber) {
        ExcelHelper.rowNumber = rowNumber;
    }

    public static int getColumnNumber() {
        return columnNumber;
    }

    public static void setColumnNumber(int columnNumber) {
        ExcelHelper.columnNumber = columnNumber;
    }

    public static void setDataFileLocation(String dataDirectory, String testDataExcelFileName){
        String dataFile = dataDirectory + testDataExcelFileName;
        testDataExcelPath = separatorsToSystem(dataFile);
        Log.info("Data File Location: " + testDataExcelPath + "\n");
    }

    public static void setExcelFileSheet(String sheetName){
        try{
            //Open The Excel File:
            Log.info("Opening the data file");
            FileInputStream ExcelFile = new FileInputStream(testDataExcelPath);
            Log.info("Getting Excel File");
            excelWorkBook = new XSSFWorkbook(ExcelFile);
            Log.info("Setting Excel File Sheet");
            excelSheet = excelWorkBook.getSheet(sheetName);
            Log.info("Complete setting Excel File Sheet");
        }catch (Exception e){
            Log.error("FAILED to set Excel File Sheet");
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static int getNumberOfRow(){
        return excelSheet.getPhysicalNumberOfRows();
    }

    public static String getCellData(int rowNumber, int colNumber){
        Log.info("Getting Cell Data...");
        cell = excelSheet.getRow(rowNumber).getCell(colNumber);
        DataFormatter formatter = new DataFormatter();
        Log.info("Return Cell Data Value: " + cell);
        return formatter.formatCellValue(cell);
    }

    public static XSSFRow getRowData(int rowNumber) {
        Log.info("Getting Row Data...");
        row = excelSheet.getRow(rowNumber);
        Log.info("Return Row data value");
        return row;
    }

    public static void setCellData(String value, int rowNumber, int colNumber){
        try{
            row = excelSheet.getRow(rowNumber);
            cell = row.getCell(colNumber);

            Log.info("Setting Cell Data...");
            if(cell == null){
                cell = row.createCell(colNumber);
                cell.setCellValue(value);
            }else {
                cell.setCellValue(value);
            }
            FileOutputStream outputFile = new FileOutputStream(testDataExcelPath);

            Log.info("Writing Data to file: "+outputFile);
            excelWorkBook.write(outputFile);
            outputFile.flush();
            outputFile.close();

            Log.info("Complete writing file");
        } catch (IOException e) {
            Log.error("FAILED to write file");
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static int getCellIndexByText(String text){
        Log.info("Getting cell index by text: " + text);
        DataFormatter formatter = new DataFormatter();
        row = excelSheet.getRow(0);
        int cellIndex = -1;
        if(row == null){
            Log.error("Header row is empty");
        }
        for(Cell cell:row){
            String textCell = formatter.formatCellValue(cell);
            if(textCell.equals(text)){
                cellIndex= cell.getColumnIndex();
                Log.info("Found Cell with column index: "+ cell.getColumnIndex());
            }
        }
        if (cellIndex != -1){
            return cellIndex;
        }else {
            Log.info("No cell is found in header");
            System.out.println("No cell is found in header");
        }
        return cellIndex;
    }

    public static void setupExcelTestData(String DataDirectory,String testDataExcelName, String ExcelSheetName){
        Log.info("Setting up Test Data");
//        InitialData.getProjectDirectory();
        ExcelHelper.setDataFileLocation(DataDirectory, testDataExcelName);
        ExcelHelper.setExcelFileSheet(ExcelSheetName);
    }
}
