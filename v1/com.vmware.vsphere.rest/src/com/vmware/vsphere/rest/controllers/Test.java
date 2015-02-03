package com.vmware.vsphere.rest.controllers;

import java.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.vmware.vsphere.rest.helpers.ViConnection;
import com.hubspot.jackson.jaxrs.PropertyFiltering;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;

import com.vmware.vim25.VirtualMachineConfigSpec;

@Path("/test")
public class Test {
	
	final static String defaults = "id,name";
	
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public String getTest(@Context HttpHeaders headers) {

		//ManagedObjectReference mor = new ManagedObjectReference();
		
		//VirtualMachine vm = null;
		ServiceInstance si = new ViConnection().getServiceInstance(headers, null, "pvvvspm005.directv.com");
		Folder rootFolder = si.getRootFolder();
		ManagedEntity[] vms;
		try {
			vms = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
			
			return vms[0].getName();
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}

		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("spec")
	public VirtualMachineConfigSpec getTest2(@Context HttpHeaders headers) {
		VirtualMachineConfigSpec spec = new VirtualMachineConfigSpec();
		
		spec.alternateGuestName = "";
		
		
		return spec;
		
	}
	
}
