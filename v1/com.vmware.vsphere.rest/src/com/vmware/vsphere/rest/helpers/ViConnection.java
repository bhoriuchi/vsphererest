package com.vmware.vsphere.rest.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;

public class ViConnection {

	private HttpHeaders headers;
	private String viServer;
	private ServiceInstance si;
	private static String basic = "Basic ";
	
	public ViConnection() { }
	
	public ViConnection(HttpHeaders headers, String viServer) {
		this.headers = headers;
		this.viServer = viServer;
	}
	
	public ServiceInstance getServiceInstance() {
		
		// check input
		if (this.viServer == null || this.viServer == "") { return null; }
		if (this.headers == null || (this.headers instanceof HttpHeaders) == false) {return null; }
		
		// format the SDK string
		String sdk = "https://" + this.viServer + "/sdk";

		// get the basic authentication header
		List<String> authHeaders = this.headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
		String base64Credentials = null;
		ServiceInstance si = null;
		
		for (String header : authHeaders) {
			if (header.indexOf(basic) != -1) {
				base64Credentials = header.substring(header.indexOf(basic) + basic.length());
				break;
			}
		}
		
		// check if basic authentication was found
		if (base64Credentials == null) { return null; }
		
		// decode header into its parts
		Decoder decoder = Base64.getDecoder();
        String credentials = new String(decoder.decode(base64Credentials));
        final String[] values = credentials.split(":",2);

        // create a new service instance
        try {
			si = new ServiceInstance(new URL(sdk),values[0], values[1], true);
		} catch (RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        
        this.si = si;
        
		return si;
	}
	
	public ServiceInstance getServiceInstance(HttpHeaders headers, String viServer) {
		this.headers = headers;
		this.viServer = viServer;
		return getServiceInstance();
	}
	
	public ManagedEntity[] getEntities(String type) {
		
		
		
		try {
			Folder rootFolder = this.si.getRootFolder();
			return new InventoryNavigator(rootFolder).searchManagedEntities(type);
		} catch (NullPointerException | RemoteException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
	public ManagedEntity[] getEntities(String type, HttpHeaders headers, String viServer) {
		getServiceInstance(headers, viServer);
		return getEntities(type);
	}
	
	public ManagedEntity getEntity(String type, String id) {

		try {
			Folder rootFolder = this.si.getRootFolder();
			ManagedEntity[] entities = new InventoryNavigator(rootFolder).searchManagedEntities(type);

			for (ManagedEntity e : entities) {

				if (e.getMOR().getVal().toLowerCase().equals(id.toLowerCase())) { return e; }
			}
			
		}  catch (NullPointerException | RemoteException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return null;
	}
	
	public ManagedEntity getEntity(String type, String id, HttpHeaders headers, String viServer) {
		getServiceInstance(headers, viServer);
		return getEntity(type, id);
	}
	
	public HttpHeaders getHeaders() {
		return this.headers;
	}
	public void setHttpHeaders(HttpHeaders headers) {
		this.headers = headers;
	}
	
	public String getViServer() {
		return this.viServer;
	}
	public void setViServer(String viServer) {
		this.viServer = viServer;
	}
	
}
