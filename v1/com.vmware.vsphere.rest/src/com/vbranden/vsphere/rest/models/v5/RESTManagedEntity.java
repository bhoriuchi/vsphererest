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

package com.vbranden.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Response;

import com.vbranden.vsphere.rest.helpers.ArrayHelper;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.AlarmState;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.Event;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.Permission;
import com.vmware.vim25.Tag;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Task;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTManagedEntity extends RESTExtensibleManagedObject {

	private boolean alarmActionsEnabled;
	private Event[] configIssue;
	private ManagedEntityStatus configStatus;
	private CustomFieldValue[] customValue;
	private AlarmState[] declaredAlarmState;
	private String[] disabledMethod;
	private int[] effectiveRole;
	private String label;
	private String name;
	private ManagedEntityStatus overallStatus;
	private String parent;
	private Permission[] permission;
	private List<String> recentTask;
	private Tag[] tag;
	private AlarmState[] triggeredAlarmState;

	public void setManagedEntity(ManagedEntity mo, String fields, String uri) {

		FieldGet fg = new FieldGet();

		try {
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
			if (fg.get("label", fields)) {
				this.setLabel(mo.getName());
			}
			if (fg.get("name", fields)) {
				this.setName(mo.getName());
			}
			if (fg.get("overallStatus", fields)) {
				this.setOverallStatus(mo.getOverallStatus());
			}
			if (fg.get("parent", fields)) {
				this.setParent(new ManagedObjectReferenceUri().getUri(
						mo.getParent(), uri));
			}
			if (fg.get("permission", fields)) {
				this.setPermission(mo.getPermission());
			}
			if (fg.get("recentTask", fields)) {

				this.setRecentTask(new ManagedObjectReferenceArray()
						.getMORArray(mo.getRecentTasks(), uri));
			}
			if (fg.get("tag", fields)) {
				this.setTag(mo.getTag());
			}
			if (fg.get("triggeredAlarmState", fields)) {
				this.setTriggeredAlarmState(mo.getTriggeredAlarmState());
			}

		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setExtensibleManagedObject(mo, fields, uri);
	}

	/*
	 * get all objects of this type
	 */
	public Response getAll(String vimType, String vimClass, String restClass,
			ViConnection vi, String search, String fieldStr, String thisUri, int start,
			int position, int results) {

		try {

			ManagedEntity[] e = vi.getEntities(vimType);

			List<Object> m = new ManagedObjectReferenceArray().getObjectArray(
					e, Class.forName(vimClass), Class.forName(restClass),
					search, thisUri, fieldStr, position, start, results, false);

			if (m == null) {
				return Response.status(404).build();
			} else if (m.size() == 0) {
				return Response.status(204).build();
			} else {
				return Response.ok().entity(m).build();
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return Response.status(404).build();
	}

	/*
	 * remove this object
	 */
	public Response remove(String vimType, String vimClass, String restClass,
			ViConnection vi, String fields, String thisUri, String id) {
		try {

			// Get the entity that matches the id
			ManagedEntity m = vi.getEntity(vimType, id);

			if (m != null) {

				Class<?> vim = Class.forName(vimClass);
				Method method = vim.getMethod("destroy_Task");
				Task t = (Task) method.invoke(vim.cast(m));

				URI uri = new URI(thisUri + t.getMOR().getType().toLowerCase()
						+ "s/" + t.getMOR().getVal());
				return Response.created(uri)
						.entity(new RESTTask(t, thisUri, fields)).build();
			} else {
				return Response.status(404).build();
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * create a child object of this type
	 */
	public Response createChild(String vimType, String vimClass,
			String restClass, ViConnection vi, String apiVersion, String fields,
			String thisUri, String id, String childType, RESTRequestBody body) {

		// define the allowed child types
		HashMap<String, String[]> allowedChildren = new HashMap<String, String[]>();
		allowedChildren.put("Datacenter", new String[] { "Folder",
				"ClusterComputeResource", "HostSystem", "VirtualMachine",
				"VmwareDistributedVirtualSwitch" });
		allowedChildren.put("ClusterComputeResource", new String[] {
				"HostSystem", "VirtualMachine", "ResourcePool", "VirtualApp" });
		allowedChildren.put("HostSystem", new String[] { "VirtualMachine" });
		allowedChildren.put("ResourcePool", new String[] { "VirtualMachine",
				"ResourcePool", "VirtualApp" });
		allowedChildren.put("Folder", new String[] { "Folder",
				"VirtualMachine", "VirtualApp" });
		allowedChildren.put("VmwareDistributedVirtualSwitch", new String[] {
				"HostSystem", "DistributedVirtualPortGroup" });

		ArrayHelper h = new ArrayHelper();
		String vimChildType = h.containsCaseInsensitiveGet(childType,
				allowedChildren.get(vimType));

		if (!allowedChildren.containsKey(vimType) || vimChildType == null) {
			return Response.status(404).build();
		} else {
			if (vimChildType == "ClusterComputeResource") {
				body.setDatacenter(id);
				return new RESTClusterComputeResource().create("", "", "", vi, fields, thisUri, body);
			}
		}

		return null;
	}

	/**
	 * @return the alarmActionsEnabled
	 */
	public boolean isAlarmActionsEnabled() {
		return alarmActionsEnabled;
	}

	/**
	 * @param alarmActionsEnabled
	 *            the alarmActionsEnabled to set
	 */
	public void setAlarmActionsEnabled(boolean alarmActionsEnabled) {
		this.alarmActionsEnabled = alarmActionsEnabled;
	}

	/**
	 * @return the configIssue
	 */
	public Event[] getConfigIssue() {
		return configIssue;
	}

	/**
	 * @param configIssue
	 *            the configIssue to set
	 */
	public void setConfigIssue(Event[] configIssue) {
		this.configIssue = configIssue;
	}

	/**
	 * @return the configStatus
	 */
	public ManagedEntityStatus getConfigStatus() {
		return configStatus;
	}

	/**
	 * @param configStatus
	 *            the configStatus to set
	 */
	public void setConfigStatus(ManagedEntityStatus configStatus) {
		this.configStatus = configStatus;
	}

	/**
	 * @return the customValue
	 */
	public CustomFieldValue[] getCustomValue() {
		return customValue;
	}

	/**
	 * @param customValue
	 *            the customValue to set
	 */
	public void setCustomValue(CustomFieldValue[] customValue) {
		this.customValue = customValue;
	}

	/**
	 * @return the declaredAlarmState
	 */
	public AlarmState[] getDeclaredAlarmState() {
		return declaredAlarmState;
	}

	/**
	 * @param declaredAlarmState
	 *            the declaredAlarmState to set
	 */
	public void setDeclaredAlarmState(AlarmState[] declaredAlarmState) {
		this.declaredAlarmState = declaredAlarmState;
	}

	/**
	 * @return the disabledMethod
	 */
	public String[] getDisabledMethod() {
		return disabledMethod;
	}

	/**
	 * @param disabledMethod
	 *            the disabledMethod to set
	 */
	public void setDisabledMethod(String[] disabledMethod) {
		this.disabledMethod = disabledMethod;
	}

	/**
	 * @return the effectiveRole
	 */
	public int[] getEffectiveRole() {
		return effectiveRole;
	}

	/**
	 * @param effectiveRole
	 *            the effectiveRole to set
	 */
	public void setEffectiveRole(int[] effectiveRole) {
		this.effectiveRole = effectiveRole;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the overallStatus
	 */
	public ManagedEntityStatus getOverallStatus() {
		return overallStatus;
	}

	/**
	 * @param overallStatus
	 *            the overallStatus to set
	 */
	public void setOverallStatus(ManagedEntityStatus overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return the permission
	 */
	public Permission[] getPermission() {
		return permission;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(Permission[] permission) {
		this.permission = permission;
	}

	/**
	 * @return the recentTask
	 */
	public List<String> getRecentTask() {
		return recentTask;
	}

	/**
	 * @param recentTask
	 *            the recentTask to set
	 */
	public void setRecentTask(List<String> recentTask) {
		this.recentTask = recentTask;
	}

	/**
	 * @return the tag
	 */
	public Tag[] getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(Tag[] tag) {
		this.tag = tag;
	}

	/**
	 * @return the triggeredAlarmState
	 */
	public AlarmState[] getTriggeredAlarmState() {
		return triggeredAlarmState;
	}

	/**
	 * @param triggeredAlarmState
	 *            the triggeredAlarmState to set
	 */
	public void setTriggeredAlarmState(AlarmState[] triggeredAlarmState) {
		this.triggeredAlarmState = triggeredAlarmState;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
