package com.vmware.vsphere.rest.helpers;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class OutputFilter {

	private String fields;
	private Object object;
	
	public OutputFilter() { }
	
	public OutputFilter(String fields, Object object) {
		this.fields = fields;
		this.object = object;
	}
	
	
	public String getOutput() throws JsonProcessingException {
		
		// get fields requested
	    StringTokenizer st = new StringTokenizer(this.fields, ",");
	    Set<String> filterProperties = new HashSet<String>();
	    while (st.hasMoreTokens()) { filterProperties.add(st.nextToken()); }
	    
	    ObjectMapper mapper = new ObjectMapper();
	    FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filterProperties));
		return mapper.writer(filters).writeValueAsString(this.object);
	}
	
	public String getOutput(String fields, Object object) throws JsonProcessingException {
		this.fields = fields;
		this.object = object;
		return getOutput();
	}
	
	/*
	public String getDotFilteredOutput() {
	
	    StringTokenizer st = new StringTokenizer(this.fields, ",");
	    Set<String> filterProperties = new HashSet<String>();
	    while (st.hasMoreTokens()) { filterProperties.add(st.nextToken()); }
	    
	    String output = "";
	    ObjectMapper mapper = new ObjectMapper();
		
		
		return "";
	}
	
	
	
	
	public String getDotFilteredOutput(String fields, Object object) {
		this.fields = fields;
		this.object = object;	
		return getDotFilteredOutput(); 
	}*/
	
	
	
	public String getFields() {
		return this.fields;
	}
	public Object getObject() {
		return this.object;
	}
	
}
