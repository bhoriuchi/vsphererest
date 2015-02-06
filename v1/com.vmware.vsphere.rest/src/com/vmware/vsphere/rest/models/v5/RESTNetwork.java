package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vmware.vim25.NetworkSummary;
import com.vmware.vim25.mo.Network;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.FieldGet;

public class RESTNetwork extends RESTManagedEntity {
	
	private List<String> host;
	private NetworkSummary summary;
	private List<String> vm;
	
	// constructor
	public RESTNetwork() {
	}

	// overloaded constructor
	public RESTNetwork(Network mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(Network mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields			
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(mo.getHosts(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}
			if (fg.get("vm", fields)) {
				this.setVm(new ManagedObjectReferenceArray().getMORArray(mo.getVms(), uri));
			}

			// set the extended properties
			this.setManagedEntity(mo, fields, uri);


		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public NetworkSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(NetworkSummary summary) {
		this.summary = summary;
	}

	/**
	 * @return the vm
	 */
	public List<String> getVm() {
		return vm;
	}

	/**
	 * @param vm the vm to set
	 */
	public void setVm(List<String> vm) {
		this.vm = vm;
	}

}
