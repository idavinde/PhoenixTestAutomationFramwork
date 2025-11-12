package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	private ExcelReaderUtil2() {
		
	}
	public static Iterator<UserCredentials> loadTestData()  {
		// Apache POI OOXML LIB

		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkbook=null;
		try {
			myWorkbook = new XSSFWorkbook(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Focus on the sheet

		XSSFSheet mySheet = myWorkbook.getSheet("LoginTestData");

		XSSFRow myRow;
		XSSFCell myCell;

		// Want to know the index for the username and password in our sheet.
		XSSFRow header = mySheet.getRow(0);

		int usernameIndex = -1;
		int passwordIndex = -1;

		for (Cell cell : header) {

			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {

				usernameIndex = cell.getColumnIndex();
			}

			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {

				passwordIndex = cell.getColumnIndex();
			}
		}
		
		System.out.println(usernameIndex+ "  "+  passwordIndex);
		
		int lastRowIndex = mySheet.getLastRowNum();
		XSSFRow rowData;
		UserCredentials userCredentials;
		List<UserCredentials> list= new ArrayList<UserCredentials>();
		
		for(int rowIndex=1 ; rowIndex<=lastRowIndex;rowIndex++) {
			
			rowData= mySheet.getRow(rowIndex);
			
			userCredentials = new UserCredentials(rowData.getCell(usernameIndex).toString(), rowData.getCell(passwordIndex).toString());
			list.add(userCredentials);
			
		}
		
		return list.iterator();
	}

}
