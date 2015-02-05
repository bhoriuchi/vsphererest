package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTVmwareDistributedVirtualSwitch extends RESTDistributedVirtualSwitch {
	
	// constructor
	public RESTVmwareDistributedVirtualSwitch() {
	}

	// overloaded constructor
	public RESTVmwareDistributedVirtualSwitch(VmwareDistributedVirtualSwitch mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(VmwareDistributedVirtualSwitch mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("networkResourcePool", fields)) {
				this.setNetworkResourcePool(mo.getNetworkResourcePool());
			}
			if (fg.get("portgroup", fields)) {
				this.setPortgroup(new ManagedObjectReferenceArray().getMORArray(mo.getPortgroup(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("uuid", fields)) {
				this.setUuid(mo.getUuid());
			}
			
			
			// extended from RESTManagedObject
			if (fg.get("id", fields)) {
				this.setId(mo.getMOR().getVal());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(mo.getMOR().getType() + "-" + mo.getMOR().getVal());
			}
			
			// extended from RESTExtensibleManagedObject
			if (fg.get("availableField", fields)) {
				this.setAvailableField(mo.getAvailableField());
			}		
			if (fg.get("value", fields)) {
				this.setValue(mo.getValues());
			}			
			
			// extended from RESTManagedEntity
			if (fg.get("alarmActionsEnabled", fields)) {
				this.setAlarmActionsEnabled(mo.getAlarmActionEabled());
			}
			if (fg.get("configIssue", fields)) {
				this.setConfigIssue(mo.getConfigIssue());
			}
			if (fg.get("configStatus", fields)) {
				this.setConfigStatus(mo.getConfigStatus());
			}
			if (fg.get("customValue", fields)) {
				this.setCustomValue(mo.getCustomValue());
			}
			if (fg.get("declaredAlarmState", fields)) {
				this.setDeclaredAlarmState(mo.getDeclaredAlarmState());
			}
			if (fg.get("disabledMethod", fields)) {
				this.setDisabledMethod(mo.getDisabledMethod());
			}
			if (fg.get("effectiveRole", fields)) {
				this.setEffectiveRole(mo.getEffectiveRole());
			}
			if (fg.get("name", fields)) {
				this.setName(mo.getName());
			}
			if (fg.get("overallStatus", fields)) {
				this.setOverallStatus(mo.getOverallStatus());
			}
			if (fg.get("parent", fields)) {
				this.setParent(new ManagedObjectReferenceUri().getUri(mo.getParent(), uri));
			}
			if (fg.get("permission", fields)) {
				this.setPermission(mo.getPermission());
			}
			if (fg.get("recentTask", fields)) {
				this.setRecentTask(new ManagedObjectReferenceArray().getMORArray(mo.getRecentTasks(), uri));
			}
			if (fg.get("tag", fields)) {
				this.setTag(mo.getTag());
			}
			if (fg.get("triggeredAlarmState", fields)) {
				this.setTriggeredAlarmState(mo.getTriggeredAlarmState());
			}



		} catch (RemoteException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
