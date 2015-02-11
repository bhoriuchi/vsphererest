package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.DVSCapability;
import com.vmware.vim25.DVSConfigInfo;
import com.vmware.vim25.DVSCreateSpec;
import com.vmware.vim25.DVSNetworkResourcePool;
import com.vmware.vim25.DVSRuntimeInfo;
import com.vmware.vim25.DVSSummary;
import com.vmware.vim25.DistributedVirtualSwitchProductSpec;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.DvsFault;
import com.vmware.vim25.DvsNotAuthorized;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.NotFound;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VMwareDVSConfigSpec;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.DistributedVirtualSwitch;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.Task;
import com.vmware.vsphere.rest.helpers.ArrayHelper;
//import com.vmware.vsphere.rest.helpers.DefaultValuesHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTDistributedVirtualSwitch extends RESTManagedEntity {

	private DVSCapability capability;
	private DVSConfigInfo config;
	private DVSNetworkResourcePool[] networkResourcePool;
	private List<String> portgroup;
	private DVSRuntimeInfo runtime;
	private DVSSummary summary;
	private String uuid;

	// constructor
	public RESTDistributedVirtualSwitch() {
	}

	// overloaded constructor
	public RESTDistributedVirtualSwitch(DistributedVirtualSwitch mo,
			String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(DistributedVirtualSwitch mo, String uri, String fields) {
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
				this.setPortgroup(new ManagedObjectReferenceArray()
						.getMORArray(mo.getPortgroup(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("uuid", fields)) {
				this.setUuid(mo.getUuid());
			}

			// set the extended properties
			this.setManagedEntity(mo, fields, uri);

		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * create a new object of this type
	 */
	public Response create(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fields, String thisUri, RESTRequestBody body) {

		// initialize a custom response
		RESTCustomResponse cr = new RESTCustomResponse("",
				new ArrayList<String>());
		
		if (body == null) {
			
			cr.setResponseStatus("failed");
			cr.getResponseMessage().add("No message body was specified in the request");
			
			return Response.status(400).entity(cr).build();
		}
		
		// instantiate helpers
		// DefaultValuesHelper dh = new DefaultValuesHelper().init();
		ArrayHelper ah = new ArrayHelper();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();

		// create a new service instance
		ViConnection v = new ViConnection(headers, sessionKey, viServer);
		Folder f = null;

		
		try {

			// get the parent folder
			if (body.getDatacenter() != null) {

				Datacenter dc = (Datacenter) v.getEntity("Datacenter",
						body.getDatacenter());
				f = dc.getNetworkFolder();
			} else if (body.getParentFolder() != null) {

				f = (Folder) v.getEntity("Folder", body.getParentFolder());
			}

			// check if the folders child types for DistributedVirtualSwitch or
			// if the folder is null
			if (f == null) {
				
				cr.setResponseStatus("failed");
				cr.getResponseMessage().add("The Network Folder was not found. A Network Folder or Datacenter must be specified");
				
				return Response
						.status(400)
						.entity(cr)
						.build();
			} else if (ah.containsCaseInsensitive("DistributedVirtualSwitch",
					f.getChildType())) {

				Task t = null;

				// try to create the dvswitch
				if (body.getName() != null) {

					// create a default config spec
					DVSCreateSpec spec = new DVSCreateSpec();
					VMwareDVSConfigSpec dvSpec = new VMwareDVSConfigSpec();
					dvSpec.setName(body.getName());
					spec.setConfigSpec(dvSpec);

					// check if a specific dvSwitch version was requested
					if (body.getVersion() != null) {
						DistributedVirtualSwitchProductSpec pSpec = new DistributedVirtualSwitchProductSpec();
						pSpec.setVersion(body.getVersion());
						spec.setProductInfo(pSpec);
					}

					// create the dvswitch
					t = f.createDVS_Task(spec);

				} else if (body.getSpec() != null) {

					t = f.createDVS_Task((DVSCreateSpec) body.getSpec());

				} else {
					cr.setResponseStatus("failed");
					cr.getResponseMessage().add("Missing DVSCreateSpec or mandatory parameters");
					
					return Response
							.status(400)
							.entity(cr)
							.build();
				}

				// check if the task was created
				if (t != null) {
					return Response.created(new URI(moUri.getUri(t, thisUri)))
							.entity(new RESTTask(t, thisUri, fields)).build();
				}

			}
		} catch (DvsNotAuthorized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DvsFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if this was reached the switch creation failed
		cr.setResponseStatus("failed");
		cr.getResponseMessage().add("Failed to create the DistributedVirtualSwitch");
		return Response
				.status(400)
				.entity(cr)
				.build();
	}

	/**
	 * @return the capability
	 */
	public DVSCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
	 */
	public void setCapability(DVSCapability capability) {
		this.capability = capability;
	}

	/**
	 * @return the config
	 */
	public DVSConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(DVSConfigInfo config) {
		this.config = config;
	}

	/**
	 * @return the networkResourcePool
	 */
	public DVSNetworkResourcePool[] getNetworkResourcePool() {
		return networkResourcePool;
	}

	/**
	 * @param networkResourcePool
	 *            the networkResourcePool to set
	 */
	public void setNetworkResourcePool(
			DVSNetworkResourcePool[] networkResourcePool) {
		this.networkResourcePool = networkResourcePool;
	}

	/**
	 * @return the portgroup
	 */
	public List<String> getPortgroup() {
		return portgroup;
	}

	/**
	 * @param portgroup
	 *            the portgroup to set
	 */
	public void setPortgroup(List<String> portgroup) {
		this.portgroup = portgroup;
	}

	/**
	 * @return the runtime
	 */
	public DVSRuntimeInfo getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime
	 *            the runtime to set
	 */
	public void setRuntime(DVSRuntimeInfo runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the summary
	 */
	public DVSSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(DVSSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
