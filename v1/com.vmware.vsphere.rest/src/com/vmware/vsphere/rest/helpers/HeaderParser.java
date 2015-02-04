package com.vmware.vsphere.rest.helpers;


import java.util.List;
import javax.ws.rs.core.HttpHeaders;


public class HeaderParser {
	
	private HttpHeaders headers;
	private String type;
	private String search;
	
	public HeaderParser() {}
	
	public HeaderParser(HttpHeaders headers, String type) {
		this.setHeaders(headers);
		this.setType(type);
	}
	
	public HeaderParser(HttpHeaders headers, String type, String search) {
		this.setHeaders(headers);
		this.setType(type);
		this.setSearch(search);
	}
	

	public String getHeader(HttpHeaders headers, String type, String search) {
		this.setSearch(search);
		return getHeader(headers, type);
	}
	
	public String getHeader(HttpHeaders headers, String type) {
		this.setHeaders(headers);
		this.setType(type);
		return getHeader();
	}
	
	
	public String getHeader() {
		
		// validate that the headers and type are not null
		if (this.getHeaders() == null
				|| (this.getHeaders() instanceof HttpHeaders) == false || this.getType() == null) {
			return null;
		}
		
		// get the headers by name
		List<String> headers = this.getHeaders().getRequestHeader(this.getType());

		// search is specified
		if (this.getSearch() != null) {
			for (String header : headers) {
				if (header.indexOf(this.getSearch()) != -1) {
					return header;
				}
			}
		}
		else if (headers.size() > 0) {
			return headers.get(0);
		}
		
		return null;
	}
	
	
	
	
	
	
	/**
	 * @return the headers
	 */
	public HttpHeaders getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

}
