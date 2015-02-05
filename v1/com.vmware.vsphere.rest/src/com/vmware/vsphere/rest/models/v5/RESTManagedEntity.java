package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.AlarmState;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.Event;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.Permission;
import com.vmware.vim25.Tag;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Task;
import com.vmware.vsphere.rest.helpers.ArrayHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTManagedEntity extends RESTExtensibleManagedObject {
	
	private boolean alarmActionsEnabled;
	private Event[] configIssue;
	private ManagedEntityStatus configStatus;
	private CustomFieldValue[] customValue;
	private AlarmState[] declaredAlarmState;
	private String[] disabledMethod;
	private int[] effectiveRole;
	private String name;
	private ManagedEntityStatus overallStatus;
	private String parent;
	private Permission[] permission;
	private List<String> recentTask;
	private Tag[] tag;
	private AlarmState[] triggeredAlarmState;
	
	
	
	
	/*
	 * get all objects of this type
	 */
	public Response getAll(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers,
			String sessionKey, String search,
			String fieldStr, String thisUri, int start, int position,
			int results) {

		try {

			ManagedEntity[] e = new ViConnection().getEntities(vimType,
					headers, sessionKey, viServer);

			List<Object> m = new ManagedObjectReferenceArray().getObjectArray(e,
					Class.forName(vimClass), Class.forName(restClass), search, thisUri,
					fieldStr, position, start, results, false);
			
			if (m == null) {
				return Response.status(404).build();
			}
			else if (m.size() == 0) {
				return Response.status(204).build();
			}
			else {
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
	 * get a specific object of this type by id
	 */
	public Response getById(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fieldStr, String thisUri, String id) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity(vimType, id,
					headers, sessionKey, viServer);

			if (m != null) {
				
				Class<?> vim = Class.forName(vimClass);
				Class<?> rest = Class.forName(restClass);
				Object mo = rest.newInstance();
				
				// create parameter/argument array and init the rest class
				Class<?> params[] = { vim, String.class, String.class };
				Object args[] = { vim.cast(m), thisUri, fieldStr };
				Method method = rest.getMethod("init", params);
				method.invoke(mo, args);
				
				
				//RESTDatacenter e = new RESTDatacenter((Datacenter) m, thisUri, fieldStr);
				return Response.ok().entity(mo).build();
			} else {
				return Response.status(404).build();
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return Response.status(404).build();
	}
	
	
	
	
	/*
	 * remove this object
	 */
	public Response remove(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fields, String thisUri, String id) {
		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity(vimType, id,
					headers, sessionKey, viServer);

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
			String restClass, String viServer, HttpHeaders headers,
			String sessionKey, String apiVersion, String fields,
			String thisUri, String id, String childType, RESTRequestBody body) {
		
		// define the allowed child types
		HashMap<String, String[]> allowedChildren = new HashMap<String, String[]>();
		allowedChildren.put("Datacenter", new String[] {"Folder", "ClusterComputeResource", "HostSystem", "VirtualMachine", "VmwareDistributedVirtualSwitch"});
		allowedChildren.put("ClusterComputeResource", new String[] {"HostSystem", "VirtualMachine", "ResourcePool", "VirtualApp"});
		allowedChildren.put("HostSystem", new String[] {"VirtualMachine"});
		allowedChildren.put("ResourcePool", new String[] {"VirtualMachine", "ResourcePool", "VirtualApp"});
		allowedChildren.put("Folder", new String[] {"Folder", "VirtualMachine", "VirtualApp"});
		allowedChildren.put("VmwareDistributedVirtualSwitch", new String[] {"HostSystem", "DistributedVirtualPortGroup"});
		
		ArrayHelper h = new ArrayHelper();
		String vimChildType = h.containsCaseInsensitiveGet(childType, allowedChildren.get(vimType));
		
		if (!allowedChildren.containsKey(vimType) || vimChildType == null) {
			return Response.status(404).build();
		}
		else {
			if (vimChildType == "ClusterComputeResource") {
				body.setDatacenter(id);
				return new RESTClusterComputeResource().create("", "", "", viServer, headers, sessionKey, fields, thisUri, body);
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
	 * @param alarmActionsEnabled the alarmActionsEnabled to set
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
	 * @param configIssue the configIssue to set
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
	 * @param configStatus the configStatus to set
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
	 * @param customValue the customValue to set
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
	 * @param declaredAlarmState the declaredAlarmState to set
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
	 * @param disabledMethod the disabledMethod to set
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
	 * @param effectiveRole the effectiveRole to set
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
	 * @param name the name to set
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
	 * @param overallStatus the overallStatus to set
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
	 * @param parent the parent to set
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
	 * @param permission the permission to set
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
	 * @param recentTask the recentTask to set
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
	 * @param tag the tag to set
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
	 * @param triggeredAlarmState the triggeredAlarmState to set
	 */
	public void setTriggeredAlarmState(AlarmState[] triggeredAlarmState) {
		this.triggeredAlarmState = triggeredAlarmState;
	}
}
