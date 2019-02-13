package utilities.helper;

import com.hansencx.solutions.logger.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.FORMULA;

/**
 * @param
 * @author Nhi Dinh, Vi Nguyen
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
     * Getters and Setters
     */
    public XSSFWorkbook getExcelWorkBook() {
        return excelWorkBook;
    }

    public XSSFSheet getExcelSheet() {
        return excelSheet;
    }

    /**
     * Constructor
     *
     * @param filePath
     * @param sheetName
     * @throws FileNotFoundException
     * @author Vi Nguyen
     */
    public ExcelHelper(String filePath, String sheetName) throws FileNotFoundException {
        this.filePath = filePath;
        this.sheetName = sheetName;
        openFile();
    }

    /**
     * open the excel file
     *
     * @param
     * @return String value of the cell
     * @author Vi Nguyen
     * @see
     * @since 2019-01-29
     */
    public void openFile() {
        try {
            Log.info("Opening the data file:" + filePath);
            excelFile = new FileInputStream(filePath);
            Log.info("Getting Excel File");
            excelWorkBook = new XSSFWorkbook(excelFile);
            Log.info("Setting Excel File Sheet");
            excelSheet = getExcelWorkBook().getSheet(sheetName);
            FormulaEvaluator evaluator = getExcelWorkBook().getCreationHelper().createFormulaEvaluator();
            Log.info("Complete setting Excel File Sheet");

        } catch (Exception e) {
            Log.error("FAILED to set Excel File Sheet");
            Log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * force recalculate cell
     *
     * @param
     * @return nothing
     * @author Vi Nguyen
     * @see
     * @since 2019-01-29
     */
    public void forceFormulaRecalculation() {
        this.getExcelWorkBook().setForceFormulaRecalculation(true);
    }

    /**
     * close the excel ffile
     *
     * @param
     * @return nothing
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

    /**
     * Get the index of the last row
     *
     * @param
     * @return last row index
     * @author Vi Nguyen
     * @see
     * @since 2019-01-29
     */
    public int getLastRowNum() {
        return getExcelSheet().getLastRowNum();
    }

    public int getNumberOfRow() {
        return getExcelSheet().getPhysicalNumberOfRows();
    }

    public int getNumberOfCol() {
        return getExcelSheet().getRow(0).getPhysicalNumberOfCells();
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

    public String getCellData(int rowIndex, int colIndex) {
        Log.info("Getting Cell Data...");
        XSSFCell cell = getExcelSheet().getRow(rowIndex).getCell(colIndex);

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
     * Return list of cell values in a row from cell #3 to row #7
     *
     * @param rowIndex
     * @return a XSSFCell list of parameters of the specified row(step) of the test script (Excel)
     * @author Vi Nguyen
     * @see
     * @since 2019-01-30
     */
    public List<XSSFCell> getExcelScriptsArguments(int rowIndex) {
        List<XSSFCell> listParameters = new ArrayList<>();

        //argument cell is from cell 3 -> 7 of each row
        Log.info("Getting the arguments from Excel scripts");
        for (int i = 3; i < 8; i++)
            listParameters.add(getExcelSheet().getRow(rowIndex).getCell(i));
        Log.info("Return the arguments from Excel scripts");
        return listParameters;
    }

    public XSSFRow getRowData(int rowIndex) {
        Log.info("Get and return the row data as XSSFRow");
        return getExcelSheet().getRow(rowIndex);
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
    public void setCellValue(int rowIndex, int colIndex, String value) {
        try {
            XSSFRow row = getExcelSheet().getRow(rowIndex);
            XSSFCell cell = row.getCell(colIndex);

            Log.info("Setting Cell Data...");
            if (null != cell)
                cell.setCellValue(value);
            else
                row.createCell(colIndex).setCellValue(value);
            FileOutputStream fileOut = new FileOutputStream(filePath);

            excelWorkBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeExcelValue(String[] dataToWrite) {
        Row headerRow = excelSheet.getRow(0);
        int rowCount = excelSheet.getLastRowNum();
        Row newRow = excelSheet.createRow(rowCount+1);

        for(int i=0; i< headerRow.getLastCellNum(); i++){
            Cell cell = newRow.createCell(i);
            setCellValue(rowCount+1, i, dataToWrite[i]);
        }
    }

    public int getCellIndexByText(String text) {
        Log.info("Getting cell index by text: " + text);
        DataFormatter formatter = new DataFormatter();
        XSSFRow row = excelSheet.getRow(0);
        int cellIndex = -1;
        if (row == null) {
            Log.error("Header row is empty");
        }
        for (Cell cell : row) {
            String textCell = formatter.formatCellValue(cell);
            if (textCell.equals(text)) {
                cellIndex = cell.getColumnIndex();
                Log.info("Found Cell with column index: " + cell.getColumnIndex());
            }
        }
        if (cellIndex != -1) {
            return cellIndex;
        } else {
            Log.info("No cell is found in header");
            System.out.println("No cell is found in header");
        }
        return cellIndex;
    }

    public Object[][] getTableArray() {
        String[][] tabArray = null;
        int startRow = 1;
        int startCol = 0;
        int ci, cj;
        int totalRows = getNumberOfRow();
        int totalCols = getNumberOfCol();
        tabArray = new String[totalRows-1][totalCols];
        ci = 0;
        for (int i = startRow; i < totalRows; i++, ci++) {
            cj = 0;
            for (int j = startCol; j < totalCols; j++, cj++) {
                tabArray[ci][cj] = getCellData(i, j);
                System.out.println(tabArray[ci][cj]);
            }
        }
        return (tabArray);
    }
}
