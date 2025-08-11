package org.example.UtilsExcel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtil {

    // READ data as Object[][] for TestNG
    public static Object[][] getTestData(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = row.getCell(j).toString();
            }
        }
        workbook.close();
        fis.close();
        return data;
    }

    // READ data as List of Maps (Key = Column Header)
    public static List<Map<String, String>> getDataAsMap(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> allData = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getLastCellNum();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Map<String, String> rowData = new HashMap<>();
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                rowData.put(headerRow.getCell(j).toString(), row.getCell(j).toString());
            }
            allData.add(rowData);
        }
        workbook.close();
        fis.close();
        return allData;
    }

    // CREATE a new Excel file
    public static void createExcel(String filePath, String sheetName, String[] headers, List<String[]> rows) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        for (int i = 0; i < rows.size(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < rows.get(i).length; j++) {
                row.createCell(j).setCellValue(rows.get(i)[j]);
            }
        }

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    // APPEND a row to existing Excel file
    public static void appendRow(String filePath, String sheetName, String[] rowData) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int lastRowNum = sheet.getLastRowNum();
        Row newRow = sheet.createRow(lastRowNum + 1);

        for (int i = 0; i < rowData.length; i++) {
            newRow.createCell(i).setCellValue(rowData[i]);
        }

        fis.close();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
