package com.vmware.vsphere.rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vmware.vim25.Description;
import com.vmware.vim25.HostCpuIdInfo;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.ResourceAllocationInfo;
import com.vmware.vim25.SharesInfo;
import com.vmware.vim25.SharesLevel;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDeviceBusSlotInfo;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConnectInfo;
import com.vmware.vim25.VirtualMachineAffinityInfo;
import com.vmware.vim25.VirtualMachineBootOptions;
import com.vmware.vim25.VirtualMachineBootOptionsBootableDevice;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineConsolePreferences;
import com.vmware.vim25.VirtualMachineCpuIdInfoSpec;
import com.vmware.vim25.VirtualDeviceConnectInfoStatus;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;


@Path("/{viServer}/example")
public class ExampleController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("virtualmachineconfigspec")
	public VirtualMachineConfigSpec getVirtualMachineConfigSpecExample() {
		VirtualMachineConfigSpec spec = new VirtualMachineConfigSpec();
		
		spec.alternateGuestName = "";
		spec.annotation = "";
		spec.bootOptions = new VirtualMachineBootOptions();
		spec.bootOptions.bootDelay = (long) 5000;
		spec.bootOptions.bootOrder = new VirtualMachineBootOptionsBootableDevice[1];
		spec.bootOptions.bootOrder[0] = new VirtualMachineBootOptionsBootableDevice();
		spec.bootOptions.bootRetryDelay = (long) 5000;
		spec.bootOptions.bootRetryEnabled = false;
		spec.bootOptions.enterBIOSSetup = false;
		spec.changeTrackingEnabled = false;
		spec.changeVersion = null;
		spec.consolePreferences = new VirtualMachineConsolePreferences();
		spec.consolePreferences.closeOnPowerOffOrSuspend = false;
		spec.consolePreferences.enterFullScreenOnPowerOn = false;
		spec.consolePreferences.powerOnWhenOpened = false;
		spec.cpuAffinity = new VirtualMachineAffinityInfo();
		spec.cpuAffinity.affinitySet = new int[1];
		spec.cpuAffinity.affinitySet[0] = 0;
		spec.cpuAllocation = new ResourceAllocationInfo();
		spec.cpuAllocation.expandableReservation = false;
		spec.cpuAllocation.limit = (long) -1;
		spec.cpuAllocation.overheadLimit = null;
		spec.cpuAllocation.reservation = (long) 0;
		spec.cpuAllocation.shares = new SharesInfo();
		spec.cpuAllocation.shares.dynamicType = null;
		spec.cpuAllocation.shares.dynamicProperty = null;
		spec.cpuAllocation.shares.shares = 2000;
		spec.cpuAllocation.shares.level = SharesLevel.normal;
		spec.cpuFeatureMask = null;
		spec.cpuHotAddEnabled = false;
		spec.cpuHotRemoveEnabled = false;
		spec.deviceChange = new VirtualDeviceConfigSpec[1];
		spec.deviceChange[0].device = new VirtualDevice();
		spec.deviceChange[0].device.backing = null;
		spec.deviceChange[0].device.connectable = new VirtualDeviceConnectInfo();
		spec.deviceChange[0].device.connectable.allowGuestControl = true;
		spec.deviceChange[0].device.connectable.connected = true;
		spec.deviceChange[0].device.connectable.startConnected = true;
		spec.deviceChange[0].device.connectable.status = VirtualDeviceConnectInfoStatus.ok.toString();
		spec.deviceChange[0].device.controllerKey = 100;
		spec.deviceChange[0].device.deviceInfo = new Description();
		spec.deviceChange[0].device.deviceInfo.label = "Sample label";
		spec.deviceChange[0].device.deviceInfo.summary = "Sample summary";
		spec.deviceChange[0].device.key = 100;
		spec.deviceChange[0].device.slotInfo = new VirtualDeviceBusSlotInfo();
		spec.deviceChange[0].device.unitNumber = 1;
		spec.deviceChange[0].fileOperation = VirtualDeviceConfigSpecFileOperation.create;
		spec.deviceChange[0].operation = VirtualDeviceConfigSpecOperation.add;
		spec.extraConfig = new OptionValue[1];
		spec.extraConfig[0].key = "nvram";
		spec.extraConfig[0].value = "server.nvram";
		
		
		
		return spec;
		
	}
	
	
}
