package com.exer.dupe.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.common.Levenshtein;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DupeServiceImpl implements DupeService {

	@Override
	public void findDuplicates(MultipartFile file) {

		List<String> dataList = null;
		List<String> dupeList = new ArrayList<String>();

		try {
			dataList = readCSVFile(file);

			for (int i = 0; i < dataList.size(); i++) {
				String itemToCmp = dataList.get(i);
				for (int j = i + 1; j < dataList.size(); j++) {
					int levDis = Levenshtein.distance(itemToCmp, dataList.get(j));
					int bigger = Math.max(itemToCmp.length(), dataList.get(j).length());
					double percent = (((double) bigger - (double) levDis) / (double) bigger) * 100;					
					if (percent > 50.0) { // percentage can be increased here to get a closer match
						dupeList.add(itemToCmp);
					}

				}
			}

			System.out.println("Potential Duplicates:: ");
			dupeList.forEach(System.out::println);
			
			//removing duplicates from the original list for none duplicates..
			dataList.removeAll(dupeList);
			
			System.out.println("None Duplicates:: ");
			dataList.forEach(System.out::println);
			
			

		} catch (Exception e) {
			System.out.println("Exception while finding duplicates::" + e.getMessage());
		}

	}

	private static List<String> readCSVFile(MultipartFile file) throws Exception {
		
		List<String> dataList = new ArrayList<String>();
		InputStream is = file.getInputStream();
	     ;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}

		return dataList;
	}
	
	
	//excel part of implementation

	/*
	 * private static List<String> readXlsxFile(MultipartFile file) throws Exception
	 * { List<String> dataList = null; List<String> dupeList = null; try (final
	 * XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) { final
	 * XSSFSheet sheet = workbook.getSheetAt(0); Iterator<Row> rowIterator =
	 * sheet.iterator(); DataFormatter formatter = new DataFormatter(); dataList =
	 * new ArrayList<String>(); dupeList = new ArrayList<String>();
	 * 
	 * StringBuilder sb = new StringBuilder();
	 * 
	 * while (rowIterator.hasNext()) { Row row = rowIterator.next();
	 * sb.setLength(0); if (row.getRowNum() > 0) { Iterator<Cell> cellIterator =
	 * row.cellIterator(); CellAddress address = null; while
	 * (cellIterator.hasNext()) { Cell cell = cellIterator.next(); address =
	 * cell.getAddress();
	 * 
	 * if (address.getColumn() != row.getLastCellNum())
	 * sb.append(formatter.formatCellValue(cell)).append(","); else
	 * sb.append(formatter.formatCellValue(cell)); } } if (sb != null && sb.length()
	 * > 0) dataList.add(sb.toString()); }
	 * 
	 * return dataList; } catch (Exception e) { throw e; }
	 * 
	 * }
	 */
}
