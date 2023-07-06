package org.dcuciuc.report.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dcuciuc.report.DriverService;
import org.dcuciuc.report.ExcelService;
import org.dcuciuc.report.impl.enums.GoogleDriveAPIMimeType;
import org.dcuciuc.util.PropertiesReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class ExcelServiceImpl implements ExcelService {
    private static final Logger logger = LogManager.getLogger(ExcelServiceImpl.class);
    private final String EXCEL_FILE_EXTENSION = "xlsx";
    private final PropertiesReader propertiesReader;
    private final DriverService driverService;
    private String folderName;
    private String fileName;

    public ExcelServiceImpl(DriverService driverService, PropertiesReader propertiesReader) {
        this.driverService = driverService;
        this.propertiesReader = propertiesReader;
    }

    public String saveWorkbookLocal(Workbook workbook) {
        StringBuilder filePath = new StringBuilder();
        filePath.append(propertiesReader.readProperty("file.path"));
        filePath.append("\\");
        filePath.append(folderName);

        try {
            boolean isCreated = new File(filePath.toString()).mkdir();

            filePath.append("\\");
            filePath.append(fileName);
            filePath.append(".");
            filePath.append(EXCEL_FILE_EXTENSION);

            FileOutputStream outputStream = new FileOutputStream(filePath.toString());
            workbook.write(outputStream);

            outputStream.close();

            logger.info("Excel generated successfully");
        } catch (IOException e) {
            logger.error("Error occurred while creating new output stream and writing excel file in it. "
                    + e.getMessage());
        }

        return filePath.toString();
    }

    @Override
    public void saveWorkbookServer(String filePath) {
        String folderId = driverService.findFolderIdByName(folderName);

        if (folderId == null) {
            folderId = driverService.createFolder(folderName);
        }

        File file = new File(filePath);
        driverService.saveFile(file, fileName, folderId, GoogleDriveAPIMimeType.EXCEL_XLSX);
    }

    @Override
    public void writeSheetData(Object[][] data, Sheet sheet) {
        CellStyle headerStyle = getCellStyle(sheet, true, true, (short) 300, HorizontalAlignment.CENTER);
        CellStyle dataStyle = getCellStyle(sheet, false, false, (short) 240, HorizontalAlignment.CENTER);

        int rows = data.length;
        int columns = data[0].length;
        for (int i = 0; i < rows; i++) {
            Row row = sheet.createRow(i);

            for (int j = 0; j < columns; j++) {
                Cell cell = row.createCell(j);

                if (i == 0) {
                    cell.setCellStyle(headerStyle);
                } else {
                    cell.setCellStyle(dataStyle);
                }

                Object value = data[i][j];

                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Long) {
                    cell.setCellValue((Long) value);
                } else if (value instanceof Float) {
                    cell.setCellValue((Float) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof LocalDate) {
                    cell.setCellValue((LocalDate) value);
                }
            }
        }
    }

    @Override
    public Object[][] readSheetData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream inputStream = new FileInputStream(propertiesReader.readProperty("file.path")
                + filePath + ".xlsx")) {

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            data = getDataFromSheet(sheet);

        } catch (IOException e) {
            logger.error("File with path " + filePath + " not found. " + e.getMessage());
        }
        return data;
    }

    @Override
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private CellStyle getCellStyle(Sheet sheet, boolean bold, boolean dateFormat,
                                   short fontHeight, HorizontalAlignment horizontalAlignment) {
        Font font = sheet.getWorkbook().createFont();
        font.setBold(bold);
        font.setFontHeight(fontHeight);
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();

        if (dateFormat) {
            CreationHelper creationHelper = sheet.getWorkbook().getCreationHelper();
            headerStyle.setDataFormat(creationHelper.createDataFormat().getFormat("d-mmm"));
        }

        headerStyle.setAlignment(horizontalAlignment);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private Object[][] getDataFromSheet(Sheet sheet) {
        int rows = sheet.getLastRowNum();
        int columns = sheet.getRow(1).getLastCellNum();

        Object[][] data = new Object[rows][columns];
        for (int i = 0; i < rows; i++) {
            Row row = sheet.getRow(i);

            for (int j = 0; j < columns; j++) {
                Cell cell = row.getCell(j);

                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case STRING:
                        data[i][j] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data[i][j] = cell.getLocalDateTimeCellValue().toLocalDate();
                        } else {
                            if (j < columns - 2) {
                                data[i][j] = (int) cell.getNumericCellValue();
                            } else {
                                data[i][j] = (float) cell.getNumericCellValue();
                            }
                        }
                }
            }
        }
        return data;
    }
}
