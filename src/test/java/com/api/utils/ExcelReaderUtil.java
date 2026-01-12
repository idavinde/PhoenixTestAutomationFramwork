package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);
	private ExcelReaderUtil() {
		
	}
	public static <T> Iterator<T> loadTestData(String pathOfExcelFile,String sheetName, Class<T> clazz)  {
		
		LOGGER.info("Reading the test Data from .xlsx file {} and the sheet name is {}", pathOfExcelFile, sheetName);

		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(pathOfExcelFile);
		XSSFWorkbook myWorkbook=null;
		try {
			myWorkbook = new XSSFWorkbook(input);
		} catch (IOException e) {
			LOGGER.error("Cannot read the excel {}", pathOfExcelFile, e);
			e.printStackTrace();
		}

		// Focus on the sheet

		XSSFSheet mySheet = myWorkbook.getSheet(sheetName);
		LOGGER.info("Converting the XSSFSheet {} to POJO Class of type {}", sheetName, clazz);
		
		List<T>dataList =Poiji.fromExcel(mySheet, clazz);
		return dataList.iterator();
		
		
	}
	
	

}
