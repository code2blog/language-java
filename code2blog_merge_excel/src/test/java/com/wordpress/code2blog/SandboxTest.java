package com.wordpress.code2blog;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

@Log4j2
public class SandboxTest {

    @Test
    public void should_merge_excel_files() throws Exception {
        File excelSource1 = new File("src\\main\\resources\\Dhoni.xlsx");
        File excelSource2 = new File("src\\main\\resources\\Raina.xlsx");
        File excelTarget = new File("src\\main\\resources\\15th-August-2020.xlsx");

        XSSFWorkbook workbookTarget = new XSSFWorkbook();

        XSSFWorkbook workbook1 = new XSSFWorkbook(excelSource1);
        for (int sheetIndex = 0; sheetIndex < workbook1.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet oldSheet = workbook1.getSheetAt(sheetIndex);
            XSSFSheet newSheet = workbookTarget.createSheet(String.format("%s-%s", excelSource1.getName(), workbook1.getSheetName(sheetIndex)));
            ExcelSheet.copy(oldSheet, newSheet);
        }
        XSSFWorkbook workbook2 = new XSSFWorkbook(excelSource2);
        for (int sheetIndex = 0; sheetIndex < workbook2.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet oldSheet = workbook2.getSheetAt(sheetIndex);
            XSSFSheet newSheet = workbookTarget.createSheet(String.format("%s-%s", excelSource2.getName(), workbook2.getSheetName(sheetIndex)));
            ExcelSheet.copy(oldSheet, newSheet);
        }

        FileOutputStream out = new FileOutputStream(excelTarget);
        workbookTarget.write(out);
        out.close();

        workbook1.close();
        workbook2.close();
    }
}
