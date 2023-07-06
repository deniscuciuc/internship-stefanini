package org.dcuciuc.report;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

/**
 * Excel manager is a helper interface for creating Excel files and saving/reading data from/in Excel file
 *
 * @author dcuciuc
 */
public interface ExcelService {

    /**
     * Saves Excel file in default path local, and before it can create new folder
     * that was set in constructor if such not exist
     *
     * @param workbook to be saved
     * @return
     */
    String saveWorkbookLocal(Workbook workbook);


    void saveWorkbookServer(String filePath);

    /**
     * Writes an 2D array of objects in sheet
     * <br>
     * Method supports objects: String, Long, Float, Integer, LocalDate
     *
     * @param data  to be saved in sheet
     * @param sheet in which we save our data
     */
    void writeSheetData(Object[][] data, Sheet sheet);

    /**
     * Reads data from a sheet if such sheet and file exists
     *
     * @param filePath  file path for Excel file
     * @param sheetName sheet name from which reads data
     * @return 2D array of objects (String, Long, Float, Integer, LocalDate)
     */
    Object[][] readSheetData(String filePath, String sheetName);

    void setFolderName(String folderName);

    void setFileName(String fileName);
}
