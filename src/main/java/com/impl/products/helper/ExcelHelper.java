package com.impl.products.helper;

import com.impl.products.model.product.Product;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Code", "Product Name (AR)", "Product Name (EN)", "Product Price", "Product Category (AR)"
        ,"Product Category (EN)", "Quantity"};
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<Product> excelToProducts(InputStream is) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Product> products = new ArrayList<Product>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Product product = new Product();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            product.setCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            product.setNameAr(currentCell.getStringCellValue());
                            break;
                        case 2:
                            product.setNameEn(currentCell.getStringCellValue());
                            break;
                        case 3:
                            product.setPrice((int) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            product.setCategoryNameAr(currentCell.getStringCellValue());
                            break;
                        case 5:
                            product.setCategoryNameEn(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                products.add(product);
            }
            workbook.close();
            return products;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
