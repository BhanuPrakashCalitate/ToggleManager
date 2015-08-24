package com.adam.base;

/*
 * Author Bhanu
 */

import java.io.FileInputStream;
import java.util.Properties;

public class SuiteBase {
	
	public static Properties OR;
	public FileInputStream fin;
	
	public Properties readOR(String pathOR) throws Exception{
		fin = new FileInputStream(pathOR);
		OR = new Properties(System.getProperties());
		OR.load(fin);
		return OR;
	}
	
}
