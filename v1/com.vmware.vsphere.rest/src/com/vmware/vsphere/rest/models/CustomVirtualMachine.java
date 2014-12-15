package com.vmware.vsphere.rest.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.VirtualMachine;

@JsonFilter("PropertyFilter")
public class CustomVirtualMachine {
	
	
	private String[] properties;
	private String id;
	private String name;
	private VirtualMachineSummary summary;
	private VirtualMachineConfigInfo config;
	private GuestInfo guest;
	

	public CustomVirtualMachine() { }

	public CustomVirtualMachine(VirtualMachine vm) {
		
		this.properties = new String[] {"id", "name", "summary", "config", "properties", "guest"};
		this.id = vm.getMOR().getVal();
		this.name = vm.getName();
		this.summary = vm.getSummary();
		this.config = vm.getConfig();
		this.guest = vm.getGuest();
	}
	
	public String[] getProperties() { return this.properties; }
	public String getId() { return this.id; }
	public String getName() { return this.name; }
	public VirtualMachineSummary getSummary() { return this.summary; }
	public VirtualMachineConfigInfo getConfig() { return this.config; }
	public GuestInfo getGuest() { return this.guest; }

}
