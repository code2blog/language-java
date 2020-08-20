package com.wordpress.code2blog;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;

@Log4j2
public class SandboxTest {

    @Test
    public void should_read_excel_files() throws Exception{
        File excelSource1 = new File("src\\main\\resources\\Dhoni.xlsx");
        if(!excelSource1.exists()){
            throw new RuntimeException("input file not found : " + excelSource1.getAbsolutePath());
        }
        File excelSource2 = new File("src\\main\\resources\\Raina.xlsx");
        File excelTarget = new File("src\\main\\resources\\15th-August-2020.xlsx");
        XSSFWorkbook workbook1 = new XSSFWorkbook(excelSource1);
        for(int sheetIndex=0; sheetIndex<workbook1.getNumberOfSheets(); sheetIndex++){
            log.info("on the first excel file, I found sheet : " + workbook1.getSheetName(sheetIndex));
        }
        XSSFWorkbook workbook2 = new XSSFWorkbook(excelSource2);
        for(int sheetIndex=0; sheetIndex<workbook2.getNumberOfSheets(); sheetIndex++){
            log.info("on the second excel file, I found sheet : " + workbook2.getSheetName(sheetIndex));
        }
        workbook1.close();
        workbook2.close();
    }
}
