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

package com.vbranden.vsphere.rest.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.codec.binary.Base64;

import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ManagedObject;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.util.MorUtil;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class ViConnection {

	private HttpHeaders headers;
	private String viServer;
	private ServiceInstance si;
	private ServerConnection sc;
	private String sdk;
	private String SessionKey;
	private String sessionString;
	private boolean endSessionOnComplete;
	private static String basic = "Basic ";

	// constructor
	public ViConnection() {
	}

	// overloaded constructor
	public ViConnection(HttpHeaders headers, String sessionKey, String viServer) {
		this.setHeaders(headers);
		this.setViServer(viServer);
		this.setSessionKey(sessionKey);
		if (viServer != null) {
			this.setSdk("https://" + viServer + "/sdk");
		}
	}
	
	// overloaded getServiceInstance
	public ServiceInstance getServiceInstance(HttpHeaders headers,
			String sessionKey, String viServer) {
		this.setHeaders(headers);
		this.setViServer(viServer);
		this.setSessionKey(sessionKey);
		if (viServer != null) {
			this.setSdk("https://" + viServer + "/sdk");
		}
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
				this.setSi(new ServiceInstance(new URL(this.getSdk()), session,
						true));
				
				if (this.getSi() != null && this.getSi().currentTime() != null) {
					
					// set the flag to keep the session alive on completing
					SessionIdHelper keyHelper = new SessionIdHelper(this.getSi());
					this.setSessionString(keyHelper.getSessionString());
					this.setEndSessionOnComplete(false);

					// return the service instance
					return this.getSi();
				}
			}

			// otherwise try basic authentication
			else {

				String base64Credentials = null;
				String authHeader = new HeaderParser().getHeader(headers,
						HttpHeaders.AUTHORIZATION, basic);

				if (authHeader != null) {
					
					base64Credentials = authHeader.substring(authHeader
							.indexOf(basic) + basic.length());

					// decode header into its parts
					byte[] credBytes = Base64.decodeBase64(base64Credentials);
					String credentials = new String(credBytes);

					final String[] values = credentials.split(":", 2);

					// try to connect and set the service instance
					this.setSi(new ServiceInstance(new URL(this.getSdk()),
							values[0], values[1], true));
					
					if (this.getSi() != null && this.getSi().currentTime() != null) {
						// get the session key
						SessionIdHelper keyHelper = new SessionIdHelper(this.getSi());
						this.setSessionKey(keyHelper.getSessionId());
						this.setSessionString(keyHelper.getSessionString());
						this.setEndSessionOnComplete(true);
						
						// return the service instance
						return this.getSi();
					}
				}
			}

		} catch (MalformedURLException e1) {
			//e1.printStackTrace();
			System.out.println("MalformedURLException from getServiceInstance");
		} catch (RemoteException e) {
			//e.printStackTrace();
			System.out.println("RemoteException from getServiceInstance");
		}

		return null;
		
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
			return new InventoryNavigator(rootFolder)
					.searchManagedEntities(type);

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

		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ManagedObjectReference mor = new ManagedObjectReference();
		mor.setType(type);
		mor.setVal(moUri.getId(id));

		try {
			ManagedEntity mo = MorUtil.createExactManagedEntity(this.getSi()
					.getServerConnection(), mor);
			
			// call the getAvailableField method to cause an exception on 
			// managed objects that do not exist
			mo.getAvailableField();

			return mo;
		} catch (NullPointerException e) {

			return null;
		}
		catch (Exception e) {
			System.out.println("General Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific entity with all arguments
	public ManagedEntity getEntity(String type, String id, HttpHeaders headers,
			String sessionKey, String viServer) {
		getServiceInstance(headers, sessionKey, viServer);
		return getEntity(type, id);
	}

	// get a managed object
	public ManagedObject getManagedObject(String type, String id) {

		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
		ManagedObjectReference mor = new ManagedObjectReference();
		mor.setType(type);
		mor.setVal(moUri.getId(id));
		
		ManagedObject mo = MorUtil.createExactManagedObject(this.getSi()
				.getServerConnection(), mor);
		
		return mo;
	}

	// get a specific object with all arguments
	public ManagedObject getManagedObject(String type, String id,
			HttpHeaders headers, String sessionKey, String viServer) {
		getServiceInstance(headers, sessionKey, viServer);
		return getManagedObject(type, id);
	}

	// close a session with a force
	public void closeSession(boolean force) {

		this.setEndSessionOnComplete(force);
		this.closeSession();
	}
	
	// close a session
	public void closeSession() {
		
		try {

			if (this.isEndSessionOnComplete()) {

				if (this.getSi().getSessionManager() != null) {
					
					// log session out
					this.getSi().getSessionManager().logout();
				} else {
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
		
		if (this.si == null) {
			this.setSi(this.getServiceInstance());
		}
		
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

	/**
	 * @return the endSessionOnComplete
	 */
	public boolean isEndSessionOnComplete() {
		return endSessionOnComplete;
	}

	/**
	 * @param endSessionOnComplete the endSessionOnComplete to set
	 */
	public void setEndSessionOnComplete(boolean endSessionOnComplete) {
		this.endSessionOnComplete = endSessionOnComplete;
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
