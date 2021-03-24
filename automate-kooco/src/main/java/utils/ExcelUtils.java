package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import enums.ConfigProperties;

public class ExcelUtils {
	private static FileOutputStream fos;
	private static XSSFWorkbook workbook;
	// private static XSSFSheet sheet;
	// private static XSSFRow row;
	private static FileInputStream fis;
	private static String date = DateFormat.getDateInstance().format(new Date()).replace(":", "-").replaceAll("[,\s]",
			"");

	private ExcelUtils() {
	}

	public static String getFilePath() {
		return PropertyUtils.getProperty(ConfigProperties.KOOCO_FILEPATH.name().toLowerCase());
	}

	public static void createWorkbook() throws IOException {
		fos = new FileOutputStream(getFilePath());
		workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet_1");
		XSSFRow row = sheet.createRow(0);
		row.createCell(0, CellType.STRING).setCellValue("DATE");
		row.createCell(1, CellType.STRING).setCellValue("CURRENT_BALANCE");
		row.createCell(2, CellType.STRING).setCellValue("TODAY_REVENUE");
		row.createCell(3, CellType.STRING).setCellValue("TODAY_PROFIT");
		row.createCell(4, CellType.STRING).setCellValue("TOTAL_REVENUE");
		row.createCell(5, CellType.STRING).setCellValue("ORDER_PROFIT_TODAY");
		row.createCell(6, CellType.STRING).setCellValue("TOTAL_MONEY");
		row.createCell(7, CellType.STRING).setCellValue("TODAY_TEAM_COMMISSION");
		row.createCell(8, CellType.STRING).setCellValue("TOTAL_TEAM_COMMISSION");
		row.createCell(9, CellType.STRING).setCellValue("AVAILABLE_FOR_WITHDRAWAL");
		row.createCell(10, CellType.STRING).setCellValue("ORDERS_DONE");
		int totalColumns = sheet.getRow(0).getLastCellNum();
		for (int i = 0; i < totalColumns; i++) {
			sheet.autoSizeColumn(i);
		}
		workbook.write(fos);
		fos.close();
		workbook.close();
	}

	public static void createOrBackup() {
		Path oldFile = Path.of(getFilePath());
		if (!Files.exists(oldFile)) {
			try {
				// create new excel file
				createWorkbook();
				System.out.println("New kooco.xlsx created.");
			} catch (IOException e) {
				System.err.println("Exception occurred!!!");
				e.printStackTrace();
			}
			return;
		}

		if ("Y".equalsIgnoreCase(PropertyUtils.getProperty(ConfigProperties.KOOCO_TAKE_BACKUP.name().toLowerCase()))) {
			try {
				Files.copy(oldFile, Path.of(".//kooco_" + date + "_"
						+ PropertyUtils.getProperty(ConfigProperties.KOOCO_USERNAME.name().toLowerCase()) + ".xlsx"),
						StandardCopyOption.REPLACE_EXISTING);
				System.out.println("kooco.xlsx backup done.");
			} catch (IOException e1) {
				System.err.println("Cannot rename the file");
				e1.printStackTrace();
			}
		}
	}

	public static void writeToExcel(List<String> data) {
		workbook = openExcelFile();
		XSSFSheet sheet = workbook.getSheetAt(0);
		int totalColumns = sheet.getRow(0).getLastCellNum(); // column indexing starts from 1
		int lastRow = sheet.getLastRowNum();
		XSSFRow row = sheet.createRow(lastRow + 1); // // row indexing starts from 0
		for (int i = 0; i < totalColumns; i++) {
			XSSFCell cell = row.createCell(i, CellType.STRING);
			cell.setCellValue(data.get(i));
			sheet.autoSizeColumn(i);
		}

		try {
			fos = new FileOutputStream(getFilePath());
			workbook.write(fos);
			fis.close();
			fos.close();
			workbook.close();
			System.out.println("kooco.xlsx updated successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getYesterdayRevenue() {
		workbook = openExcelFile();
		if (workbook.getSheetAt(0).getLastRowNum() != 0) {
			int lastRowNumber = workbook.getSheetAt(0).getLastRowNum();
			XSSFRow row = workbook.getSheetAt(0).getRow(lastRowNumber);
			return row.getCell(2).getStringCellValue().trim().replace("₹", "");
		}

		return "0";
	}

	private static XSSFWorkbook openExcelFile() {
		try {
			fis = new FileInputStream(getFilePath());
			workbook = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			System.err.println("kooco.xlsx not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Cannot read kooco.xlsx");
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			System.err.println("Cannot close kooco.xlsx");
			e.printStackTrace();
		}
		return workbook;
	}
}
