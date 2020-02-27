package com.wordpress.code2blog.mail2excel;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmailToExcel {
    static Logger logger = Logger.getLogger(EmailToExcel.class);

    public static Row getRow(XSSFSheet sheet, int rowNumber) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }
        return row;
    }

    public static Map<String, String> parseEml(List<String> lines) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String line : lines) {
            if (line.trim().length() == 0) {
                continue;
            }
            String[] columnNameAndValues = line.split(" :", 2);
            if (columnNameAndValues.length != 2) {
                logger.warn(String.format("Line [%s] is missing colon delimiter", line));
                continue;
            }
            map.put(columnNameAndValues[0], columnNameAndValues[1]);
        }
        return map;
    }

    public static Map<String, String> parseEmlFile(File inputFile) throws Exception {
        List<String> lines = FileUtils.readLines(inputFile);
        return parseEml(lines);
    }

}
