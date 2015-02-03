package com.vmware.vsphere.rest.controllers;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vsphere.rest.helpers.ViConnection;


@Path("/{viServer}/sessionkey")
public class SessionKeyController {
	
	
	/*
	 * get a session key
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer) {

		ServiceInstance si = new ViConnection().getServiceInstance(headers, null, viServer);

		if (si != null) {
			String keyStr = si.getServerConnection().getSessionStr();
			
			if (keyStr != null) {

				Pattern p = Pattern.compile("\"(.*?)\"");
				Matcher m = p.matcher(keyStr);
				
				if(m.find()) {
					String key = m.group(1);
					
					Hashtable<String, String> keyObject = new Hashtable<String, String>();
					keyObject.put("sessionKey", key);
					
					return Response.ok().entity(keyObject).build();
				}	
			}
		}
		else {
			return Response.status(401).build();
		}
		
		return null;
	}
	
	
}
