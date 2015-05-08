package com.vbranden.vsphere.rest.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private String propertyFile;
	
	public PropertyReader() {}
	
	public PropertyReader(String propertyFile) {
		this.setPropertyFile(propertyFile);
	}
	
	
	public String getProperty(String propertyFile, String property) throws IOException {
		this.setPropertyFile(propertyFile);
		return this.getProperty(property);
	}
	
	public String getProperty(String property) throws IOException {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream(this.getPropertyFile()));

		return prop.getProperty(property);
	}
	

	/**
	 * @return the propertyFile
	 */
	public String getPropertyFile() {
		return propertyFile;
	}

	/**
	 * @param propertyFile the propertyFile to set
	 */
	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}
}
