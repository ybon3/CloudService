package com.nkg.ventilator.service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class XyzDescExportService extends BaseService {

	public static final List<Map<String, Object>> FAKE_RESULT = new ArrayList<>();
	/**
	 * 機台商品文字敘述.xlsx
	 */
	public Path buildProductDescXLSX() throws Exception {

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			buildSheet(workbook, "TW機台", FAKE_RESULT);
			buildSheet(workbook, "US機台", FAKE_RESULT);
			buildSheet(workbook, "JP機台", FAKE_RESULT);
			buildSheet(workbook, "TH機台", FAKE_RESULT);
			buildSheet(workbook, "EU機台", FAKE_RESULT);

			//output to temp file
			Path tempFile = Files.createTempFile("buildProductDescXLSX", null);
			try (FileOutputStream outputStream = new FileOutputStream(tempFile.toFile())) {
				workbook.write(outputStream);
			}

			return tempFile;
		}
		catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 耗材、備品文字敘述.xlsx
	 */
	public Path buildSparePartXLSX() throws Exception {

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			buildSheet(workbook, "TW耗材", FAKE_RESULT);
			buildSheet(workbook, "US耗材", FAKE_RESULT);
			buildSheet(workbook, "JP耗材", FAKE_RESULT);
			buildSheet(workbook, "TH耗材", FAKE_RESULT);
			buildSheet(workbook, "EU耗材", FAKE_RESULT);

			buildSheet(workbook, "TW備品", FAKE_RESULT);
			buildSheet(workbook, "US備品", FAKE_RESULT);
			buildSheet(workbook, "JP備品", FAKE_RESULT);
			buildSheet(workbook, "TH備品", FAKE_RESULT);
			buildSheet(workbook, "EU備品", FAKE_RESULT);

			//output to temp file
			Path tempFile = Files.createTempFile("buildProductDescXLSX", null);
			try (FileOutputStream outputStream = new FileOutputStream(tempFile.toFile())) {
				workbook.write(outputStream);
			}

			return tempFile;
		}
		catch (Exception e) {
			throw e;
		}
	}

	private void buildSheet(XSSFWorkbook workbook, String sheetName, List<Map<String, Object>> resultList) {
		XSSFSheet sheet = workbook.createSheet(sheetName);

		if (resultList.isEmpty()) {
			logger.info("Sheet: " + sheetName + " no data");
			return;
		}

		int colIndex = 0;
		int rowIndex = 0;

		//header
		String[] headers = resultList.get(0).keySet().toArray(new String[0]);
		Row row = sheet.createRow(rowIndex++);
		for (String h : headers) {
			Cell cell = row.createCell(colIndex++);
			cell.setCellValue(h);
		}

		//row data
		for (Map<String, Object> data : resultList) {
			row = sheet.createRow(rowIndex++);

			colIndex = 0;
			for (String h : headers) {
				Cell cell = row.createCell(colIndex++);
				Object value = data.get(h);
				if (value == null) {
					cell.setCellValue("NULL");
				}
				else {
					if (h.equals("publish_status")) {
						value = (Boolean)value?"1":"0";
					}
					cell.setCellValue(value.toString());
				}
			}
		}

		//auto size column
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}
	}
}
