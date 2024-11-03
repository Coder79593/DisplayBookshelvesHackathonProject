package utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class excel {

	public static void writeToExcel(String filePath, List<List<String>> data) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Bookshelves");
		
		for(int i = 0; i < data.size(); i++) {
			Row row = sheet.createRow(i);
			List<String> rowData = data.get(i);
			for(int j=0; j < rowData.size(); j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(rowData.get(j));
			}
		}
		
		try(FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
		}
		workbook.close();
	}
}
