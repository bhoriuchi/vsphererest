package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.DatacenterConfigInfo;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.DistributedVirtualPortgroup;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VmwareDistributedVirtualSwitch;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTDatacenter extends RESTManagedEntity {

	private DatacenterConfigInfo configuration;
	private List<String> datastore;
	private String datastoreFolder;
	private String hostFolder;
	private List<String> network;
	private String networkFolder;
	private String vmFolder;

	// constructor
	public RESTDatacenter() {
	}

	// overloaded constructor
	public RESTDatacenter(Datacenter mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	/*
	 * initialize the object
	 */
	public void init(Datacenter mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("configuration", fields)) {
				this.setConfiguration(mo.getConfiguration());
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray()
						.getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("datastoreFolder", fields)) {
				this.setDatastoreFolder(new ManagedObjectReferenceUri().getUri(
						mo.getDatastoreFolder(), uri));
			}
			if (fg.get("hostFolder", fields)) {
				this.setHostFolder(new ManagedObjectReferenceUri().getUri(
						mo.getHostFolder(), uri));
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(
						mo.getNetworks(), uri));
			}
			if (fg.get("networkFolder", fields)) {
				this.setNetworkFolder(new ManagedObjectReferenceUri().getUri(
						mo.getNetworkFolder(), uri));
			}
			if (fg.get("vmFolder", fields)) {
				this.setVmFolder(new ManagedObjectReferenceUri().getUri(
						mo.getVmFolder(), uri));
			}

			// set the extended properties
			this.setManagedEntity(mo, fields, uri);

		} catch (RemoteException | InvocationTargetException
				| NoSuchMethodException e) {
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

		ViConnection vi = new ViConnection(headers, sessionKey, viServer);
		ServiceInstance si = vi.getServiceInstance();
		Folder rootFolder = si.getRootFolder();
		ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();

		try {

			if (body.getName() != null) {
				Datacenter dc = rootFolder.createDatacenter(body.getName());
				
				return Response.created(new URI(moUri.getUri(dc, thisUri)))
						.entity(new RESTDatacenter(dc, thisUri, fields))
						.build();
			} else {
				return Response
						.status(400)
						.entity(new RESTCustomResponse("badRequest",
								"name not specified")).build();
			}
		} catch (InvalidName e) {
			return Response
					.status(400)
					.entity(new RESTCustomResponse("invalidName", body
							.getName() + " is not a valid name")).build();
		} catch (DuplicateName e) {
			return Response
					.status(400)
					.entity(new RESTCustomResponse("duplicateName", body
							.getName() + " already exists")).build();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * update this object
	 */
	public Response update(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fields, String thisUri, String id, RESTRequestBody body) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id,
					headers, sessionKey, viServer);

			if (m != null) {

				Datacenter mo = (Datacenter) m;
				ManagedObjectReferenceUri moUri = new ManagedObjectReferenceUri();

				// check if a new name was specified
				if (body.getName() != null) {
					Task t = mo.rename_Task(body.getName());

					return Response.created(new URI(moUri.getUri(t, thisUri)))
							.entity(new RESTTask(t, thisUri, fields)).build();
				} else {
					return Response
							.status(400)
							.entity(new RESTCustomResponse("badRequest",
									"missing new name value")).build();
				}
			} else {
				return Response.status(404).build();
			}

		} catch (NullPointerException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * get this objects children
	 */
	public Response getChildren(String vimType, String vimClass,
			String restClass, String viServer, HttpHeaders headers,
			String sessionKey, String search, String fieldStr, String thisUri,
			String id, String childType, int start, int position, int results) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id,
					headers, sessionKey, viServer);

			if (m == null) {
				return Response.status(404).build();
			}

			// type cast the object
			Datacenter mo = (Datacenter) m;
			Object e = null;

			if (childType.toLowerCase().equals("datastore")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getDatastoreFolder().getChildEntity(),
						Datastore.class, RESTDatastore.class, search, thisUri,
						fieldStr, position, start, results, true);
			} else if (childType.toLowerCase().equals("hostsystem")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getHostFolder().getChildEntity(), HostSystem.class,
						RESTHostSystem.class, search, thisUri, fieldStr,
						position, start, results, true);
			} else if (childType.toLowerCase().equals("clustercomputeresource")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getHostFolder().getChildEntity(),
						ClusterComputeResource.class,
						RESTClusterComputeResource.class, search, thisUri,
						fieldStr, position, start, results, false);
			} else if (childType.toLowerCase().equals("network")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getNetworkFolder().getChildEntity(), Network.class,
						RESTNetwork.class, search, thisUri, fieldStr, position,
						start, results, true);
			} else if (childType.toLowerCase().equals(
					"distributedvirtualportgroup")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getNetworkFolder().getChildEntity(),
						DistributedVirtualPortgroup.class,
						RESTDistributedVirtualPortgroup.class, search, thisUri,
						fieldStr, position, start, results, true);
			} else if (childType.toLowerCase().equals(
					"vmwaredistributedvirtualswitch")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getNetworkFolder().getChildEntity(),
						VmwareDistributedVirtualSwitch.class,
						RESTVmwareDistributedVirtualSwitch.class, search,
						thisUri, fieldStr, position, start, results, true);
			} else if (childType.toLowerCase().equals("virtualmachine")) {

				e = new ManagedObjectReferenceArray().getObjectArray(mo
						.getVmFolder().getChildEntity(), VirtualMachine.class,
						RESTVirtualMachine.class, search, thisUri, fieldStr,
						position, start, results, true);
			}

			if (e == null) {
				return Response.status(404).build();
			} else {
				return Response.ok().entity(e).build();
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (InvalidProperty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(404).build();

	}


	
	/**
	 * @return the configuration
	 */
	public DatacenterConfigInfo getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(DatacenterConfigInfo configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the datastore
	 */
	public List<String> getDatastore() {
		return datastore;
	}

	/**
	 * @param datastore
	 *            the datastore to set
	 */
	public void setDatastore(List<String> datastore) {
		this.datastore = datastore;
	}

	/**
	 * @return the datastoreFolder
	 */
	public String getDatastoreFolder() {
		return datastoreFolder;
	}

	/**
	 * @param datastoreFolder
	 *            the datastoreFolder to set
	 */
	public void setDatastoreFolder(String datastoreFolder) {
		this.datastoreFolder = datastoreFolder;
	}

	/**
	 * @return the hostFolder
	 */
	public String getHostFolder() {
		return hostFolder;
	}

	/**
	 * @param hostFolder
	 *            the hostFolder to set
	 */
	public void setHostFolder(String hostFolder) {
		this.hostFolder = hostFolder;
	}

	/**
	 * @return the network
	 */
	public List<String> getNetwork() {
		return network;
	}

	/**
	 * @param network
	 *            the network to set
	 */
	public void setNetwork(List<String> network) {
		this.network = network;
	}

	/**
	 * @return the networkFolder
	 */
	public String getNetworkFolder() {
		return networkFolder;
	}

	/**
	 * @param networkFolder
	 *            the networkFolder to set
	 */
	public void setNetworkFolder(String networkFolder) {
		this.networkFolder = networkFolder;
	}

	/**
	 * @return the vmFolder
	 */
	public String getVmFolder() {
		return vmFolder;
	}

	/**
	 * @param vmFolder
	 *            the vmFolder to set
	 */
	public void setVmFolder(String vmFolder) {
		this.vmFolder = vmFolder;
	}

}
