package com.api.utils;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String path = "Config/config.properties";
	private static String env;

	private ConfigManager() {

	}

	static {
		env = System.getProperty("env","qa");
		env= env.toLowerCase().trim();
		
		switch (env) {
		
		case "dev"-> path = "Config/config.dev.properties";
			
		case "qa" -> path = "Config/config.qa.properties";
			
		case "uat" -> path = "Config/config.uat.properties";
			
		default-> path = "Config/config.properties";
		}
		
		
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)
;		
		if(input==null) {
			
			throw new RuntimeException("Cannot find the file path");
		}
		
		try {
			prop.load(input);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);

	}
}
