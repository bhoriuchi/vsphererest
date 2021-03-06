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

package com.vbranden.vsphere.rest.controllers;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vbranden.vsphere.rest.helpers.SessionIdHelper;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ServiceInstance;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@Path("/{viServer}/sessionkey")
public class SessionKeyController {
	
	
	/*
	 * get a session key
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer) {

		ViConnection vi = new ViConnection();
		ServiceInstance si = vi.getServiceInstance(headers, null, viServer);
		SessionIdHelper keyHelper = new SessionIdHelper();

		if (si != null) {
			
			String key = keyHelper.getSessionId(si);
			
			if (key != null) {
					
				Hashtable<String, String> keyObject = new Hashtable<String, String>();
				keyObject.put("sessionKey", key);
				vi.setEndSessionOnComplete(false);
				return Response.ok().entity(keyObject).build();
			}
		}
		else {
			return Response.status(401).build();
		}
		return null;
	}
	
	@Path("/{sessionKey}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEntity(@Context HttpHeaders headers, @PathParam("viServer") String viServer, 
			@PathParam("sessionKey") String sessionKey) {
		
		ViConnection vi = new ViConnection();
		ServiceInstance si = vi.getServiceInstance(headers, sessionKey, viServer);
		try {
			
			if (si != null) {
				si.getSessionManager().logout();
				return Response.ok().build();
			}
			
		} catch (RuntimeFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return Response.status(404).build();
	}
	
	
}
