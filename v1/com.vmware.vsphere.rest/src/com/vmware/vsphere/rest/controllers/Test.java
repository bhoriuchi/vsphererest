package com.vmware.vsphere.rest.controllers;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.vmware.vim25.DVPortgroupConfigSpec;
import com.vmware.vim25.VMwareDVSPortSetting;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanIdSpec;

@Path("/test")
public class Test {
	
	final static String defaults = "id,name";
	
	@GET
	//@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getTest(@Context HttpHeaders headers) {

		
		DVPortgroupConfigSpec spec = new DVPortgroupConfigSpec();
		VMwareDVSPortSetting ps = new VMwareDVSPortSetting();
		VmwareDistributedVirtualSwitchVlanIdSpec vs = new VmwareDistributedVirtualSwitchVlanIdSpec();
		
		vs.setVlanId(123);
		vs.setInherited(false);
		ps.setVlan(vs);
		
		spec.setDefaultPortConfig(ps);
		
		
		return spec;
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
