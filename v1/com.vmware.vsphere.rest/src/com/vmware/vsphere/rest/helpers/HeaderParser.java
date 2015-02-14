/*================================================================================
Copyright (c) 2015 Branden Horiuchi. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vmware.vsphere.rest.helpers;


import java.util.List;
import javax.ws.rs.core.HttpHeaders;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/


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
