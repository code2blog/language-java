package com.wordpress.code2blog.mail2excel;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wordpress.code2blog.mail2excel.EmailToExcel.getRow;
import static com.wordpress.code2blog.mail2excel.EmailToExcel.parseEml;

public class EmailToExcelTest {
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void should_read_eml_files_from_folder_and_load_to_excel() throws Exception {
        File folder = new File("").getAbsoluteFile();
        File[] emlFiles = folder.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".eml");
            }
        });
        if (emlFiles == null || emlFiles.length == 0) {
            logger.error(String.format("No emails found in current folder [%s] for program to process.", folder.getAbsolutePath()));
            return;
        }
        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Solar");
        int rownum = 1; // Header on row 0 and details from row 1 onwards
        Set<String> columnHeaders = new LinkedHashSet<String>();
        Map<Integer, Map> productDetailsFromAllEmls = new HashMap<Integer, Map>();
        for (File emlFile : emlFiles) {
            Pattern pattern = Pattern.compile("> (Product Name.*?)> Quote Now", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(FileUtils.readFileToString(emlFile));
            if (matcher.find()) {
                String product = matcher.group(1).replace(">=20", "")
                        .replace("=46", "F")
                        .replace("\r\n> ", "")
                        .replace("=20", "");
                //logger.info(product);
                Map<String, String> map = parseEml(Arrays.asList(product.split("\r\n")));
                map.put("EML Name", emlFile.getName());
                Set<String> keys = map.keySet();
                columnHeaders.addAll(keys);
                productDetailsFromAllEmls.put(rownum, map);
                //
                rownum++;
            } else {
                logger.warn(String.format("Product details not found for file [%s]", emlFile.getName()));
            }
        }
        List<String> specialChars = Arrays.asList(new String[]{"%", "="});
        Set<String> columnHeadersCleansed = new LinkedHashSet<String>();
        int index = 0;
        outerLabel:
        for (String key : columnHeaders) {
            sheet.setColumnWidth(index++, 20 * 256);
            innerLabel:
            for (String specialChar : specialChars) {
                if (key.contains(specialChar)) {
                    logger.warn(String.format("Code fix required to correct special Chars in column header [%s]", key));
                    continue outerLabel;
                }
            }
            columnHeadersCleansed.add(key);
        }
        Set<Integer> rowNumbers = productDetailsFromAllEmls.keySet();
        for (Integer rowNumber : rowNumbers) {
            Map<String, String> map = productDetailsFromAllEmls.get(rowNumber);
            int cellnum = 0;
            for (String key : columnHeadersCleansed) {
                String value = map.get(key);
                getRow(sheet, 0).createCell(cellnum).setCellValue(key);
                value = value == null ? "" : value.trim();
                getRow(sheet, rowNumber).createCell(cellnum).setCellValue(value);
                cellnum++;
            }
        }

        FileOutputStream out = new FileOutputStream(new File(folder, "results.xlsx"));
        workbook.write(out);
        out.close();
        logger.info("created excel file");
    }


}
