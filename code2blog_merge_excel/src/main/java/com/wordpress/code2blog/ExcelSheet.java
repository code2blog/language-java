package com.wordpress.code2blog;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

// source: https://stackoverflow.com/questions/5889639/apache-poi-copying-sheets
// author: https://stackoverflow.com/users/4828463/faraz
public class ExcelSheet {

    public static void copy(XSSFSheet sheetFromOldWB, XSSFSheet sheetForNewWB) throws Exception {
        CellStyle newStyle = sheetForNewWB.getWorkbook().createCellStyle(); // Need this to copy over styles from old sheet to new sheet. Next step will be processed below
        Row row;
        Cell cell;
        for (int rowIndex = 0; rowIndex < sheetFromOldWB.getPhysicalNumberOfRows(); rowIndex++) {
            row = sheetForNewWB.createRow(rowIndex); //create row in this new sheet
            for (int colIndex = 0; colIndex < sheetFromOldWB.getRow(rowIndex).getPhysicalNumberOfCells(); colIndex++) {
                cell = row.createCell(colIndex); //create cell in this row of this new sheet
                Cell c = sheetFromOldWB.getRow(rowIndex).getCell(colIndex, Row.CREATE_NULL_AS_BLANK); //get cell from old/original WB's sheet and when cell is null, return it as blank cells. And Blank cell will be returned as Blank cells. That will not change.
                if (c.getCellType() == Cell.CELL_TYPE_BLANK) {
                    System.out.println("This is BLANK " + ((XSSFCell) c).getReference());
                } else {  //Below is where all the copying is happening. First It copies the styles of each cell and then it copies the content.
                    CellStyle origStyle = c.getCellStyle();
                    newStyle.cloneStyleFrom(origStyle);
                    cell.setCellStyle(newStyle);

                    switch (c.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            cell.setCellValue(c.getRichStringCellValue().getString());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cell.setCellValue(c.getDateCellValue());
                            } else {
                                cell.setCellValue(c.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:

                            cell.setCellValue(c.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:

                            cell.setCellValue(c.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            cell.setCellValue("who");
                            break;
                        default:
                            System.out.println();
                    }
                }
            }
        }

    }

    private void code_copied_from_stackoverflow() throws Exception {
        Workbook oldWB = new XSSFWorkbook(new FileInputStream("C:\\input.xlsx"));
        Workbook newWB = new XSSFWorkbook();
        CellStyle newStyle = newWB.createCellStyle(); // Need this to copy over styles from old sheet to new sheet. Next step will be processed below
        Row row;
        Cell cell;
        for (int i = 0; i < oldWB.getNumberOfSheets(); i++) {
            XSSFSheet sheetFromOldWB = (XSSFSheet) oldWB.getSheetAt(i);
            XSSFSheet sheetForNewWB = (XSSFSheet) newWB.createSheet(sheetFromOldWB.getSheetName());
            for (int rowIndex = 0; rowIndex < sheetFromOldWB.getPhysicalNumberOfRows(); rowIndex++) {
                row = sheetForNewWB.createRow(rowIndex); //create row in this new sheet
                for (int colIndex = 0; colIndex < sheetFromOldWB.getRow(rowIndex).getPhysicalNumberOfCells(); colIndex++) {
                    cell = row.createCell(colIndex); //create cell in this row of this new sheet
                    Cell c = sheetFromOldWB.getRow(rowIndex).getCell(colIndex, Row.CREATE_NULL_AS_BLANK); //get cell from old/original WB's sheet and when cell is null, return it as blank cells. And Blank cell will be returned as Blank cells. That will not change.
                    if (c.getCellType() == Cell.CELL_TYPE_BLANK) {
                        System.out.println("This is BLANK " + ((XSSFCell) c).getReference());
                    } else {  //Below is where all the copying is happening. First It copies the styles of each cell and then it copies the content.
                        CellStyle origStyle = c.getCellStyle();
                        newStyle.cloneStyleFrom(origStyle);
                        cell.setCellStyle(newStyle);

                        switch (c.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                cell.setCellValue(c.getRichStringCellValue().getString());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cell.setCellValue(c.getDateCellValue());
                                } else {
                                    cell.setCellValue(c.getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:

                                cell.setCellValue(c.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_FORMULA:

                                cell.setCellValue(c.getCellFormula());
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                cell.setCellValue("who");
                                break;
                            default:
                                System.out.println();
                        }
                    }
                }
            }

        }
        //Write over to the new file
        FileOutputStream fileOut = new FileOutputStream("C:\\output.xlsx");
        newWB.write(fileOut);
        oldWB.close();
        newWB.close();
        fileOut.close();
    }
}
