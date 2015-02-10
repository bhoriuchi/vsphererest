package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.DVPortgroupConfigInfo;
import com.vmware.vim25.DVPortgroupConfigSpec;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.DvsFault;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VMwareDVSPortSetting;
import com.vmware.vim25.VmwareDistributedVirtualSwitchPvlanSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchTrunkVlanSpec;
import com.vmware.vim25.VmwareDistributedVirtualSwitchVlanIdSpec;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;
import com.vmware.vsphere.rest.helpers.ArrayHelper;
import com.vmware.vsphere.rest.helpers.DefaultValuesHelper;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTDistributedVirtualPortgroup extends RESTNetwork {

	private DVPortgroupConfigInfo config;
	private String key;
	private String[] portKeys;

	// constructor
	public RESTDistributedVirtualPortgroup() {
	}

	// overloaded constructor
	public RESTDistributedVirtualPortgroup(DistributedVirtualPortgroup mo,
			String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(DistributedVirtualPortgroup mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("config", fields)) {
				this.setConfig(mo.getConfig());
			}
			if (fg.get("key", fields)) {
				this.setKey(mo.getKey());
			}
			if (fg.get("portKeys", fields)) {
				this.setPortKeys(mo.getPortKeys());
			}

			// extended from RESTNetwork
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(
						mo.getHosts(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(
						mo.getVms(), uri));
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
		
		// helps with getting default values if no value is given
		DefaultValuesHelper h = new DefaultValuesHelper().init();

		try {

			// get a connection
			ViConnection vi = new ViConnection(headers, sessionKey, viServer);
			ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();
			Task t = null;

			// the distributed virtual switch must be specified
			if (body.getDistributedVirtualSwitch() != null
					&& body.getDistributedVirtualSwitch() != "") {
				
				/*
				 *  create a defualt spec if a name was provided. this is a less complicated method for creating a configuration
				 *  with mostly default values.
				 */
				if (body.getName() != null && body.getName() != "") {
					
					// create a config spec with minimal options
					DVPortgroupConfigSpec spec = new DVPortgroupConfigSpec();
					VMwareDVSPortSetting dvps = new VMwareDVSPortSetting();
					
					// configure vlanId
					if (body.getVlanId() != -1) {

						VmwareDistributedVirtualSwitchVlanIdSpec vs = new VmwareDistributedVirtualSwitchVlanIdSpec();
						vs.setVlanId(body.getVlanId());
						vs.setInherited( (Boolean) h.set("VmwareDistributedVirtualSwitchVlanIdSpec", "inherited", body.getInherited()) );
						dvps.setVlan(vs);
						spec.setDefaultPortConfig(dvps);

					}
					
					// configure Trunk Vlan
					else if (body.getVlanTrunk() != null) {
						
						VmwareDistributedVirtualSwitchTrunkVlanSpec vs = new VmwareDistributedVirtualSwitchTrunkVlanSpec();
						ArrayHelper ah = new ArrayHelper();
						vs.setVlanId(ah.getNumericRange(body.getVlanTrunk()));
						vs.setInherited( (Boolean) h.set("VmwareDistributedVirtualSwitchTrunkVlanSpec", "inherited", body.getInherited()) );
						dvps.setVlan(vs);
						spec.setDefaultPortConfig(dvps);
					}
					
					// configure pVlan
					else if (body.getpVlanId() != -1) {
						
						VmwareDistributedVirtualSwitchPvlanSpec vs = new VmwareDistributedVirtualSwitchPvlanSpec();
						vs.setPvlanId(body.getpVlanId());
						vs.setInherited( (Boolean) h.set("VmwareDistributedVirtualSwitchPvlanSpec", "inherited", body.getInherited()) );
						dvps.setVlan(vs);
						spec.setDefaultPortConfig(dvps);
					}

					// update default spec
					spec.setName(body.getName());
					spec.setType( (String)h.set("DVPortgroupConfigSpec", "type", body.getType()) );
					spec.setNumPorts( (Integer) h.set("DVPortgroupConfigSpec", "numPorts", body.getNumPorts()) );
					spec.setDefaultPortConfig(dvps);
					
					// set the body spec to the newly created spec
					body.setSpec(spec);
				}

				// get the dvSwitch
				VmwareDistributedVirtualSwitch s = (VmwareDistributedVirtualSwitch) vi
						.getEntity("VmwareDistributedVirtualSwitch", body.getDistributedVirtualSwitch());
				
				
				if (s != null) {

					if (body.getSpecs() != null) {

						t = s.addDVPortgroup_Task((DVPortgroupConfigSpec[]) body
								.getSpecs());

					} else if (body.getSpec() != null) {

						t = s.createDVPortgroup_Task((DVPortgroupConfigSpec) body
								.getSpec());
					}

					else {
						return Response
								.status(400)
								.entity(new RESTCustomResponse("missingParameter", "no configuration spec provided")).build();						
					}
				} else {
					return Response
							.status(400)
							.entity(new RESTCustomResponse(
									"notFound",
									"DistributedVirtualSwitch-"
											+ moUri.getId(body
													.getDistributedVirtualSwitch())
											+ " not found")).build();
				}
			}

			// check if the task was created
			if (t != null) {
				return Response.created(new URI(moUri.getUri(t, thisUri)))
						.entity(new RESTTask(t, thisUri, fields)).build();
			}

		} catch (DvsFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidName e) {
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
		return null;
	}

	/**
	 * @return the config
	 */
	public DVPortgroupConfigInfo getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(DVPortgroupConfigInfo config) {
		this.config = config;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the portKeys
	 */
	public String[] getPortKeys() {
		return portKeys;
	}

	/**
	 * @param portKeys
	 *            the portKeys to set
	 */
	public void setPortKeys(String[] portKeys) {
		this.portKeys = portKeys;
	}

}
