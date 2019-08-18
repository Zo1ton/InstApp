package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelParser {

    public Map<Long, String> getExcelSheet(String pathTofile) {
        Map<Long, String> map = new HashMap<>();

        try {
            File file = new File(pathTofile);
            FileInputStream stream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row cells : sheet) {
                long id = 0;
                String name = "";
                Iterator<Cell> cellIterator = cells.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cell.getColumnIndex() == 0) {
                        id = (long) (cell.getNumericCellValue());
                    } else if (cell.getColumnIndex() == 1) {
                        name = cell.getStringCellValue();
                    }
                }
                map.put(id, name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
