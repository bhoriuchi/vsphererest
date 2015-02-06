package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vmware.vim25.ComputeResourceConfigInfo;
import com.vmware.vim25.ComputeResourceSummary;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTComputeResource extends RESTManagedEntity {
	
	private ComputeResourceConfigInfo configurationEx;
	private List<String> datastore;
	private String environmentBrowser;
	private List<String> host;
	private List<String> network;
	private String resourcePool;
	private ComputeResourceSummary summary;
	
	// constructor
	public RESTComputeResource() {
	}

	// overloaded constructor
	public RESTComputeResource(ComputeResource mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(ComputeResource mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		System.out.println("trying to create a compute resource");
		
		try {
			
			// compute resource specific fields
			if (fg.get("configurationEx", fields)) {
				this.setConfigurationEx(mo.getConfigurationEx());
			}		
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray().getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("environmentBrowser", fields)) {
				
				this.setEnvironmentBrowser(new ManagedObjectReferenceUri().getUri(mo.getEnvironmentBrowser(), uri));
			}			
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(mo.getHosts(), uri));
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(mo.getNetworks(), uri));
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(mo.getResourcePool(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}

			
			// set the extended properties
			this.setManagedEntity(mo, fields, uri);



		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * @return the configurationEx
	 */
	public ComputeResourceConfigInfo getConfigurationEx() {
		return configurationEx;
	}

	/**
	 * @param configurationEx the configurationEx to set
	 */
	public void setConfigurationEx(ComputeResourceConfigInfo configurationEx) {
		this.configurationEx = configurationEx;
	}

	/**
	 * @return the datastore
	 */
	public List<String> getDatastore() {
		return datastore;
	}

	/**
	 * @param datastore the datastore to set
	 */
	public void setDatastore(List<String> datastore) {
		this.datastore = datastore;
	}

	/**
	 * @return the environmentBrowser
	 */
	public String getEnvironmentBrowser() {
		return environmentBrowser;
	}

	/**
	 * @param environmentBrowser the environmentBrowser to set
	 */
	public void setEnvironmentBrowser(String environmentBrowser) {
		this.environmentBrowser = environmentBrowser;
	}

	/**
	 * @return the network
	 */
	public List<String> getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(List<String> network) {
		this.network = network;
	}

	/**
	 * @return the resourcePool
	 */
	public String getResourcePool() {
		return resourcePool;
	}

	/**
	 * @param resourcePool the resourcePool to set
	 */
	public void setResourcePool(String resourcePool) {
		this.resourcePool = resourcePool;
	}

	/**
	 * @return the host
	 */
	public List<String> getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(List<String> host) {
		this.host = host;
	}

	/**
	 * @return the summary
	 */
	public ComputeResourceSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(ComputeResourceSummary summary) {
		this.summary = summary;
	}



}
