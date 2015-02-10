package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.DatastoreCapability;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreInfo;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.HostNasVolumeSpec;
import com.vmware.vim25.StorageIORMInfo;
import com.vmware.vim25.VmfsDatastoreCreateSpec;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.HostDatastoreSystem;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTDatastore extends RESTManagedEntity {

	private String browser;
	private DatastoreCapability capability;
	private DatastoreHostMount[] host;
	private DatastoreInfo info;
	private StorageIORMInfo iormConfiguration;
	private DatastoreSummary summary;
	private List<String> vm;

	// constructor
	public RESTDatastore() {
	}

	// overloaded constructor
	public RESTDatastore(Datastore mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(Datastore mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// datastore specific fields
			if (fg.get("browser", fields)) {
				this.setBrowser(new ManagedObjectReferenceUri().getUri(
						mo.getBrowser(), uri));
			}
			if (fg.get("capability", fields)) {
				this.setCapability(mo.getCapability());
			}
			if (fg.get("host", fields)) {
				this.setHost(mo.getHost());
			}
			if (fg.get("info", fields)) {
				this.setInfo(mo.getInfo());
			}
			if (fg.get("iormConfiguration", fields)) {
				this.setIormConfiguration(mo.getIormConfiguration());
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

		try {

			// get a connection
			ViConnection vi = new ViConnection(headers, sessionKey, viServer);
			ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();

			if (body.getHostSystem() != null && body.getHostSystem() != "") {
				HostSystem h = (HostSystem) vi.getEntity("HostSystem",
						moUri.getId(body.getHostSystem()));
				Datastore d = null;

				HostDatastoreSystem ds = h.getHostDatastoreSystem();

				// determine the type of datastore to add
				if (body.getType() == "local" && body.getName() != null
						&& body.getPath() != null) {

					d = ds.createLocalDatastore(body.getName(), body.getPath());

				} else if (body.getType() == "nas" && body.getSpec() != null) {

					d = ds.createNasDatastore((HostNasVolumeSpec) body
							.getSpec());

				} else if (body.getType() == "vmfs" && body.getSpec() != null) {
					d = ds.createVmfsDatastore((VmfsDatastoreCreateSpec) body
							.getSpec());

				} else {
					return Response.status(400).build();
				}

				// if the datastore is not null then return it as created
				if (d != null) {
					return Response.created(new URI(moUri.getUri(d, thisUri)))
							.entity(new RESTDatastore(d, thisUri, fields))
							.build();
				}
			}

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
	 * @return the capability
	 */
	public DatastoreCapability getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
	 */
	public void setCapability(DatastoreCapability capability) {
		this.capability = capability;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser
	 *            the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the host
	 */
	public DatastoreHostMount[] getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(DatastoreHostMount[] host) {
		this.host = host;
	}

	/**
	 * @return the info
	 */
	public DatastoreInfo getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(DatastoreInfo info) {
		this.info = info;
	}

	/**
	 * @return the iormConfiguration
	 */
	public StorageIORMInfo getIormConfiguration() {
		return iormConfiguration;
	}

	/**
	 * @param iormConfiguration
	 *            the iormConfiguration to set
	 */
	public void setIormConfiguration(StorageIORMInfo iormConfiguration) {
		this.iormConfiguration = iormConfiguration;
	}

	/**
	 * @return the summary
	 */
	public DatastoreSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(DatastoreSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the vm
	 */
	public List<String> getVm() {
		return vm;
	}

	/**
	 * @param vm
	 *            the vm to set
	 */
	public void setVm(List<String> vm) {
		this.vm = vm;
	}

}
