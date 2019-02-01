package utilities.helper;

import com.hansencx.solutions.logger.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;
import utilities.configuration.InitialData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @update Jan 23, 2019
 * @since 1/15/2019
 */
public class ExcelHelper {

    private String filePath;
    private String sheetName;
    private XSSFWorkbook excelWorkBook;
    private XSSFSheet excelSheet;
    private FileInputStream excelFile;

    private DataFormatter formatter = new DataFormatter();

    /**
     * Constructor
     * @param filePath
     * @param sheetName
     * @throws FileNotFoundException
     */
    public ExcelHelper(String filePath, String sheetName) throws FileNotFoundException {

        this.filePath = filePath;
        this.sheetName = sheetName;
        openFile();
    }

    /**
     * open the excel ffile
     *
     * @param
     * @return String value of the cell
     * @author Vi Nguyen
     * @see
     * @since 2019-01-29
     */
    public void openFile() {
        try {
            Log.info("Opening the data file");
            excelFile = new FileInputStream(filePath);
            Log.info("Getting Excel File");
            excelWorkBook = new XSSFWorkbook(excelFile);
            Log.info("Setting Excel File Sheet");
            excelSheet = getExcelWorkBook().getSheet(sheetName);

            FormulaEvaluator evaluator = getExcelWorkBook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(getExcelSheet().getRow(2).getCell(3));

            Log.info("Complete setting Excel File Sheet");

        } catch (Exception e) {
            Log.error("FAILED to set Excel File Sheet");
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void forceFormulaRecalculation() {
        this.getExcelWorkBook().setForceFormulaRecalculation(true);
    }

    /**
     * close the excel ffile
     *
     * @param
     * @return String value of the cell
     * @author Vi Nguyen
     * @see
     * @since 2019-01-29
     */
    public void closeFile() {
        try {
            excelFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLastRowNum() {
        return getExcelSheet().getLastRowNum();
    }

    public int getNumberOfRow() {
        return getExcelSheet().getPhysicalNumberOfRows();
    }

    /**
     * Return String of cell
     *
     * @param rowIndex, colIndex
     * @return String value of the cell
     * @author Vi Nguyen
     * @see
     * @since 2019-01-30
     */
    public String getCellValue(int rowIndex, int colIndex) {
        XSSFCell cell = getExcelSheet().getRow(rowIndex).getCell(colIndex);
        if (null != cell) {
            if (cell.getCellType() == FORMULA)
                return cell.getRawValue();
            else
                return formatter.formatCellValue(cell);
        }
        return "";
    }

    public String getCellData(int rowNumber, int colNumber) {
        Log.info("Getting Cell Data...");
        XSSFCell cell = getExcelSheet().getRow(rowNumber).getCell(colNumber);
        //DataFormatter formatter = new DataFormatter();
        Log.info("Return Cell Data Value: " + cell);
        return formatter.formatCellValue(cell);
    }

    /**
     * Return String of cell
     *
     * @param xssfCelll
     * @return String value of the cell
     * @author Vi Nguyen
     * @see
     * @since 2019-01-30
     */
    public String getCellValue(XSSFCell xssfCelll) {

        String cellValue = "";
        if (null != xssfCelll) {
            switch (xssfCelll.getCellType()) {
                case BOOLEAN:
                    cellValue = xssfCelll.getBooleanCellValue() + "";
                    break;
                case NUMERIC:
                    cellValue = xssfCelll.getRawValue() + "";
                    break;
                case STRING:
                    cellValue = xssfCelll.getStringCellValue() + "";
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = xssfCelll.getErrorCellValue() + "";
                    break;

                // CELL_TYPE_FORMULA will never occur
                case FORMULA:
                    break;
            }

        }
        return cellValue;

    }

    /**
     * Return list of cell values in a row
     *
     * @param rowIndex
     * @return a String list of parameters of the specified row(step) of the test script (Excel)
     * @author Vi Nguyen
     * @see
     * @since 2019-01-23
     */
    public List<String> getRowValue(int rowIndex) {
        List<String> listParameters = new ArrayList<>();

        //argument cell is from cell 3 -> 7 of each row
        for (int i = 3; i < 8; i++)
            listParameters.add(getCellValue(rowIndex, i));
        return listParameters;
    }

    /**
     * Return list of cell values in a row
     *
     * @param rowIndex
     * @return a XSSFCell list of parameters of the specified row(step) of the test script (Excel)
     * @author Vi Nguyen
     * @see
     * @since 2019-01-30
     */
    public List<XSSFCell> getRow(int rowIndex) {
        List<XSSFCell> listParameters = new ArrayList<>();
        //argument cell is from cell 3 -> 7 of each row
        for (int i = 3; i < 8; i++)
            listParameters.add(getExcelSheet().getRow(rowIndex).getCell(i));
        return listParameters;
    }

    public XSSFRow getRowData(int rowNumber) {
        Log.info("Getting Row Data...");
        XSSFRow row = getExcelSheet().getRow(rowNumber);
        Log.info("Return Row data value");
        return row;
    }

    public void setCellValue(int rowIndex, int colIndex, String value) {
        XSSFRow row = getExcelSheet().getRow(rowIndex);
        XSSFCell cell = row.getCell(colIndex);
        if (null != cell)
            cell.setCellValue(value);
        else
            row.createCell(colIndex).setCellValue(value);
    }

    public void setCellData(String value, int rowNumber, int colNumber) {
        try {
            XSSFRow row = getExcelSheet().getRow(rowNumber);
            XSSFCell cell = row.getCell(colNumber);

            Log.info("Setting Cell Data...");
            if (cell == null) {
                cell = row.createCell(colNumber);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream outputFile = new FileOutputStream(filePath);

            Log.info("Writing Data to file: " + outputFile);
            getExcelWorkBook().write(outputFile);
            outputFile.flush();
            outputFile.close();

            Log.info("Complete writing file");
        } catch (IOException e) {
            Log.error("FAILED to write file");
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public XSSFWorkbook getExcelWorkBook() {
        return excelWorkBook;
    }

    public XSSFSheet getExcelSheet() {
        return excelSheet;
    }

    public int getCellIndexByText(String text){
        Log.info("Getting cell index by text: " + text);
        DataFormatter formatter = new DataFormatter();
        XSSFRow row = excelSheet.getRow(0);
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
}
