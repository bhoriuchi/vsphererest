package com.vmware.vsphere.rest.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.ws.rs.core.HttpHeaders;

import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;

public class ViConnection {

	private HttpHeaders headers;
	private String viServer;
	private ServiceInstance si;
	private ServerConnection sc;
	private String sdk;
	private String SessionKey;
	private static String basic = "Basic ";

	// constructor
	public ViConnection() {
	}

	// overloaded constructor
	public ViConnection(HttpHeaders headers, String sessionKey, String viServer) {
		this.setHeaders(headers);
		this.setViServer(viServer);
		this.setSessionKey(sessionKey);
		this.setSdk("https://" + viServer + "/sdk");
	}

	// overloaded getServiceInstance
	public ServiceInstance getServiceInstance(HttpHeaders headers,
			String sessionKey, String viServer) {
		this.setHeaders(headers);
		this.setViServer(viServer);
		this.setSessionKey(sessionKey);
		this.setSdk("https://" + viServer + "/sdk");
		return getServiceInstance();
	}

	// function that acquires a service instance
	public ServiceInstance getServiceInstance() {

		// the sdk url is always required
		if (this.getSdk() == null || this.getSdk() == "") {
			return null;
		}

		try {

			// try to do sessionKey authentication first
			if (this.getSessionKey() != null && this.getSessionKey() != "") {
				String session = "vmware_soap_session=\""
						+ this.getSessionKey() + "\"";
				this.setSi(new ServiceInstance(new URL(this.getSdk()), session, true));
			}

			// otherwise try basic authentication
			else {

				String base64Credentials = null;
				String authHeader = new HeaderParser().getHeader(headers, HttpHeaders.AUTHORIZATION, basic);
				
				if (authHeader != null) {
					base64Credentials = authHeader.substring(authHeader
							.indexOf(basic) + basic.length());

					// decode header into its parts
					Decoder decoder = Base64.getDecoder();
					String credentials = new String(
							decoder.decode(base64Credentials));
					final String[] values = credentials.split(":", 2);

					// try to connect and set the service instance
					this.setSi(new ServiceInstance(new URL(this.getSdk()), values[0],
							values[1], true));
				}
			}

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.getSi();
	}

	// get a server connection
	public ServerConnection getServerConnection(HttpHeaders headers,
			String sessionKey, String viServer) {

		if (this.getSi() == null) {
			this.getServiceInstance(headers, sessionKey, viServer);
		}

		if (this.getSi() != null) {
			ServerConnection sc = this.getSi().getServerConnection();
			this.setSc(sc);
			return sc;
		}
		return null;
	}

	// get all entities of a specific type
	public ManagedEntity[] getEntities(String type) {

		try {
			Folder rootFolder = this.getSi().getRootFolder();
			return new InventoryNavigator(rootFolder).searchManagedEntities(type);

		} catch (NullPointerException | RemoteException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}

	// get all entities of a specific type with all arguments
	public ManagedEntity[] getEntities(String type, HttpHeaders headers,
			String sessionKey, String viServer) {
		getServiceInstance(headers, sessionKey, viServer);
		return getEntities(type);
	}

	// get a specific entity
	public ManagedEntity getEntity(String type, String id) {

		try {
			Folder rootFolder = this.getSi().getRootFolder();
			ManagedEntity[] entities = new InventoryNavigator(rootFolder)
					.searchManagedEntities(type);
			for (ManagedEntity e : entities) {

				if (e.getMOR().getVal().toLowerCase().equals(id.toLowerCase())) {
					return e;
				}
			}

		} catch (NullPointerException | RemoteException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return null;
	}

	// get a specific entity with all arguments
	public ManagedEntity getEntity(String type, String id, HttpHeaders headers,
			String sessionKey, String viServer) {
		getServiceInstance(headers, sessionKey, viServer);
		return getEntity(type, id);
	}
	
	
	// close a session
	public void closeSession(boolean force) {
		
		try {
			
			if (force || this.getSessionKey() == null) {
				
				if (this.getSi().getSessionManager() != null) {
					System.out.println("logging session out");
					this.getSi().getSessionManager().logout();
				}
				else {
					System.out.println("no session to log out");
				}
			}
			
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the sc
	 */
	public ServerConnection getSc() {
		return sc;
	}

	/**
	 * @param sc
	 *            the sc to set
	 */
	public void setSc(ServerConnection sc) {
		this.sc = sc;
	}

	/**
	 * @return the si
	 */
	public ServiceInstance getSi() {
		return si;
	}

	/**
	 * @param si
	 *            the si to set
	 */
	public void setSi(ServiceInstance si) {
		this.si = si;
	}

	/**
	 * @return the sdk
	 */
	public String getSdk() {
		return sdk;
	}

	/**
	 * @param sdk
	 *            the sdk to set
	 */
	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return SessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		SessionKey = sessionKey;
	}

	/**
	 * @return the headers
	 */
	public HttpHeaders getHeaders() {
		return headers;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	/**
	 * @return the viServer
	 */
	public String getViServer() {
		return viServer;
	}

	/**
	 * @param viServer
	 *            the viServer to set
	 */
	public void setViServer(String viServer) {
		this.viServer = viServer;
	}

}
