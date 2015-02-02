package com.vmware.vsphere.rest.models;

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

	// function to initialize the object
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

			// extended from RESTManagedObject
			if (fg.get("id", fields)) {
				this.setId(mo.getMOR().getVal());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(mo.getMOR().getType() + "-"
						+ mo.getMOR().getVal());
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

		} catch (RemoteException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// get all objects
	public List<Object> getAll(String viServer, HttpHeaders headers,
			String search, String fieldStr, String thisUri, int start,
			int position, int results) {

		try {

			ManagedEntity[] e = new ViConnection().getEntities("Datacenter",
					headers, viServer);

			return new ManagedObjectReferenceArray().getObjectArray(e,
					Datacenter.class, RESTDatacenter.class, search, thisUri,
					fieldStr, position, start, results, false);

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return null;
	}

	public RESTDatacenter getById(String viServer, HttpHeaders headers,
			String fieldStr, String thisUri, String id) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id,
					headers, viServer);

			if (m != null) {
				return new RESTDatacenter((Datacenter) m, thisUri, fieldStr);
			} else {
				return null;
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Response create(String viServer, HttpHeaders headers, String fields,
			String thisUri, String id, String childTypeName, RESTRequestBody body) {

		ViConnection vi = new ViConnection(headers, viServer);
		ServiceInstance si = vi.getServiceInstance();
		Folder rootFolder = si.getRootFolder();

		try {

			if (body.getName() != null) {
				Datacenter dc = rootFolder.createDatacenter(body.getName());
				URI uri = new URI(thisUri + dc.getMOR().getType().toLowerCase()
						+ "s/" + dc.getMOR().getVal());
				return Response.created(uri)
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

	
	
	public Response createChild(String viServer, HttpHeaders headers, String fields,
			String thisUri, String id, String childTypeName, RESTRequestBody body) {

		ViConnection vi = new ViConnection(headers, viServer);
		ServiceInstance si = vi.getServiceInstance();
		Folder rootFolder = si.getRootFolder();

		
		
		return null;
	}
	
	
	
	
	
	public Response update(String viServer, HttpHeaders headers, String fields,
			String thisUri, String id, RESTRequestBody body) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id,
					headers, viServer);

			if (m != null) {

				Datacenter mo = (Datacenter) m;

				// check if a new name was specified
				if (body.getName() != null) {
					Task t = mo.rename_Task(body.getName());
					URI uri = new URI(thisUri
							+ t.getMOR().getType().toLowerCase() + "s/"
							+ t.getMOR().getVal());
					return Response.created(uri)
							.entity(new RESTTask(t, thisUri, fields)).build();
				} else {
					return Response
							.status(400)
							.entity(new RESTCustomResponse("badRequest",
									"missing new name value")).build();
				}
			} else {
				return Response
						.status(400)
						.entity(new RESTCustomResponse("badRequest", id
								+ " does not exist")).build();
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

	public Response remove(String viServer, HttpHeaders headers, String fields,
			String thisUri, String id) {
		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id,
					headers, viServer);

			if (m != null) {

				Datacenter mo = (Datacenter) m;

				Task t = mo.destroy_Task();
				URI uri = new URI(thisUri + t.getMOR().getType().toLowerCase()
						+ "s/" + t.getMOR().getVal());
				return Response.created(uri)
						.entity(new RESTTask(t, thisUri, fields)).build();
			} else {
				return Response
						.status(400)
						.entity(new RESTCustomResponse("badRequest", id
								+ " does not exist")).build();
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

	public List<Object> getChildren(String viServer, HttpHeaders headers,
			String search, String fieldStr, String thisUri, String id,
			String childType, int start, int position, int results) {

		try {

			// Get the entity that matches the id
			ManagedEntity m = new ViConnection().getEntity("Datacenter", id,
					headers, viServer);

			if (m == null) {
				return null;
			}

			// type cast the object
			Datacenter mo = (Datacenter) m;

			if (childType.toLowerCase().equals("datastore")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getDatastoreFolder().getChildEntity(),
						Datastore.class, RESTDatastore.class, search, thisUri,
						fieldStr, position, start, results, true);
			} else if (childType.toLowerCase().equals("hostsystem")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getHostFolder().getChildEntity(), HostSystem.class,
						RESTHostSystem.class, search, thisUri, fieldStr,
						position, start, results, true);
			} else if (childType.toLowerCase().equals("clustercomputeresource")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getHostFolder().getChildEntity(), ClusterComputeResource.class,
						RESTClusterComputeResource.class, search, thisUri, fieldStr,
						position, start, results, false);
			} else if (childType.toLowerCase().equals("network")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getNetworkFolder().getChildEntity(), Network.class,
						RESTNetwork.class, search, thisUri, fieldStr, position,
						start, results, true);
			} else if (childType.toLowerCase().equals("distributedvirtualportgroup")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getNetworkFolder().getChildEntity(), DistributedVirtualPortgroup.class,
						RESTDistributedVirtualPortgroup.class, search, thisUri, fieldStr, position,
						start, results, true);
			} else if (childType.toLowerCase().equals("vmwaredistributedvirtualswitch")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getNetworkFolder().getChildEntity(), VmwareDistributedVirtualSwitch.class,
						RESTVmwareDistributedVirtualSwitch.class, search, thisUri, fieldStr, position,
						start, results, true);
			} else if (childType.toLowerCase().equals("virtualmachine")) {

				return new ManagedObjectReferenceArray().getObjectArray(mo
						.getVmFolder().getChildEntity(), VirtualMachine.class,
						RESTVirtualMachine.class, search, thisUri, fieldStr,
						position, start, results, true);
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return null;
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
