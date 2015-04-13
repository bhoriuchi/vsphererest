package com.vbranden.vsphere.rest.helpers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vmware.vim25.mo.ServiceInstance;

public class SessionIdHelper {

	private ServiceInstance si;
	private String sessionString;
	
	public SessionIdHelper() {}
	
	public SessionIdHelper(ServiceInstance si) {
		this.setSi(si);
	}
	
	
	// get functions
	public String getSessionId(ServiceInstance si) {
		this.setSi(si);
		return this.getSessionId();
	}
	
	public String getSessionId() {
		if (this.getSi() != null) {
			
			// set the keystring
			this.setSessionString(this.getSi().getServerConnection().getSessionStr());
			
			// if the keystring is not null parse it for the sessionid
			if (this.getSessionString() != null) {

				Pattern p = Pattern.compile("\"(.*?)\"");
				Matcher m = p.matcher(this.getSessionString());
				
				if(m.find()) {
					return m.group(1);
				}	
			}
		}
		return null;
	}

	/**
	 * @return the si
	 */
	public ServiceInstance getSi() {
		return si;
	}

	/**
	 * @param si the si to set
	 */
	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	/**
	 * @return the sessionString
	 */
	public String getSessionString() {
		return sessionString;
	}

	/**
	 * @param sessionString the sessionString to set
	 */
	public void setSessionString(String sessionString) {
		this.sessionString = sessionString;
	}
}
