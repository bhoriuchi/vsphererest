package com.vmware.vsphere.rest.models;

import java.rmi.RemoteException;

import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.InsufficientResourcesFault;
import com.vmware.vim25.InvalidDatastore;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OutOfBounds;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.Task;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTNewVirtualMachine {

	private VirtualMachineConfigSpec config;
	private String pool;
	private String host;
	private String folder;
	
	public RESTNewVirtualMachine () { }
	
	public RESTNewVirtualMachine(String folder, String host, String pool, VirtualMachineConfigSpec config) {
		this.setFolder(folder);
		this.setHost(host);
		this.setPool(pool);
		this.setConfig(config);
	}

	public Task create(ViConnection vi) {
		
		Folder folder = (Folder) vi.getEntity("Folder", this.folder);
		ResourcePool pool = (ResourcePool) vi.getEntity("Pool", this.pool);
		HostSystem host = (HostSystem) vi.getEntity("HostSystem", this.host);
		
		try {
			return folder.createVM_Task(this.config, pool, host);
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VmConfigFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientResourcesFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDatastore e) {
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
	 * @return the config
	 */
	public VirtualMachineConfigSpec getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(VirtualMachineConfigSpec config) {
		this.config = config;
	}

	/**
	 * @return the pool
	 */
	public String getPool() {
		return pool;
	}

	/**
	 * @param pool the pool to set
	 */
	public void setPool(String pool) {
		this.pool = pool;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the folder
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * @param folder the folder to set
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
}
