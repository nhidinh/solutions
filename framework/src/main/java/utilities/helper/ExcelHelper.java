package utilities.helper;

import com.hansencx.solutions.logger.Log;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;
import utilities.configuration.InitialData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/15/2019
 * @update Jan 23, 2019
 */
public class ExcelHelper {

    private Platform platform = InitialData.PLATFORM;
    private String testDataExcelPath;
    private XSSFWorkbook excelWorkBook;
    private XSSFSheet excelSheet;
    private XSSFCell cell;
    private XSSFRow row;
    private int rowNumber;
    private int columnNumber;

    public int getRowNumber() {
        return rowNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    DataFormatter formatter = new DataFormatter();

    public void setDataFileLocation(String dataDirectory, String testDataExcelFileName){
        String dataFile = dataDirectory + testDataExcelFileName;
        setTestDataExcelPath(separatorsToSystem(dataFile));
        Log.info("Data File Location: " + testDataExcelPath + "\n");
    }

    public void setExcelFileSheet(String sheetName){
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

    public int getNumberOfRow(){
        return excelSheet.getPhysicalNumberOfRows();
    }

    public int getLastRowNum(){
        return excelSheet.getLastRowNum();
    }

    public XSSFRow getRowData(int rowNumber) {
        Log.info("Getting Row Data...");
        row = excelSheet.getRow(rowNumber);
        Log.info("Return Row data value");
        return row;
    }

    public void setCellData(String value, int rowNumber, int colNumber){
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

    public int getCellIndexByText(String text){
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

    public void setupExcelTestData(String DataDirectory,String testDataExcelName, String ExcelSheetName){
        Log.info("Setting up Test Data");
//        InitialData.getProjectDirectory();
        setDataFileLocation(DataDirectory, testDataExcelName);
        setExcelFileSheet(ExcelSheetName);
    }

    public void setTestDataExcelPath(String testDataExcelPath) {
        this.testDataExcelPath = testDataExcelPath;
    }

    // Author: Vi
    public String getCellValue(int rowIndex,  int colIndex){
        cell = excelSheet.getRow(rowIndex).getCell(colIndex);
        if(null != cell){
            if (cell.getCellType() == CellType.FORMULA)
                return cell.getRawValue();
            else
                return formatter.formatCellValue(cell);
        }
        return  "";
    }
    // Author: Nhi
    public String getCellData(int rowNumber, int colNumber){
        Log.info("Getting Cell Data...");
        cell = excelSheet.getRow(rowNumber).getCell(colNumber);
        //DataFormatter formatter = new DataFormatter();
        Log.info("Return Cell Data Value: " + cell);
        return formatter.formatCellValue(cell);
    }

    /**
     * quit driver after running a test suite
     * @author Vi Nguyen
     * @param
     * @return list of parameters of the specified row(step) of the test script (Excel)
     * @since 2019-01-23
     * @see
     */
    public List<String> getRowValue(int rowIndex){
        List<String>  listParameters = new ArrayList<>();

        //argument cell is from cell 3 -> 7 of each row
        for(int i=3; i<8; i++)
            listParameters.add(getCellValue(rowIndex, i));
        return listParameters;
    }

    public void setCellValue(int rowIndex, int colIndex, String value){
        row = excelSheet.getRow(rowIndex);
        cell = row.getCell(colIndex);
        if(null != cell)
            cell.setCellValue(value);
        else
            row.createCell(colIndex).setCellValue(value);
    }
}
