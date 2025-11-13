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
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil {

	private ExcelReaderUtil() {
		
	}
	public static <T> Iterator<T> loadTestData(String pathOfExcelFile,String sheetName, Class<T> clazz)  {
		// Apache POI OOXML LIB

		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(pathOfExcelFile);
		XSSFWorkbook myWorkbook=null;
		try {
			myWorkbook = new XSSFWorkbook(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Focus on the sheet

		XSSFSheet mySheet = myWorkbook.getSheet(sheetName);

		
		List<T>dataList =Poiji.fromExcel(mySheet, clazz);
		return dataList.iterator();
		
		
	}
	
	

}
