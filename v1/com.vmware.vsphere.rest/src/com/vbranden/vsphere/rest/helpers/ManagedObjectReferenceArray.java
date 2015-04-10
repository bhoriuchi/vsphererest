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

package com.vbranden.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class ManagedObjectReferenceArray {

	public ManagedObjectReferenceArray() {
	}

	public List<String> getMORArray(Object[] objArray, String ref)
			throws InvocationTargetException, NoSuchMethodException {

		try {

			List<String> objList = new ArrayList<String>();
			ManagedObjectReferenceUri uri = new ManagedObjectReferenceUri();

			if (objArray != null) {
				for (Object o : objArray) {
					objList.add(uri.getUri(o, ref));
				}
			}
			return objList;

		} catch (IllegalArgumentException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public List<ManagedEntity> flattenFolders(Folder f, String type, List<ManagedEntity> l)
	{

		try {
			ManagedEntity[] e = f.getChildEntity();
			
			for (ManagedEntity m : e) {
				if (m.getMOR().getType().equals("Folder")) {
					this.flattenFolders((Folder) m, type, l);
				}
				else if (m.getMOR().getType().equals(type)) {
					l.add(m);
				}
			}
			
			
		} catch (InvalidProperty e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RuntimeFault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return l;
	}
	
	
	public List<Object> getObjectArray(ManagedEntity[] mArray, Class<?> vimType, Class<?> restType,
			String search, String thisUri, String fieldStr, int position,
			int start, int results, boolean flatten) {
		int count = 0;

		List<Object> moList = new ArrayList<Object>();
		List<ManagedEntity> mlist;
		SearchParser sp = new SearchParser(search);

		try {
			for (ManagedEntity m : mArray) {

				//System.out.println(m.getMOR().getType() + " equals " + vimType.getSimpleName());
				
				if (m.getMOR().getType().equals("Folder") && flatten) {
					mlist = this.flattenFolders((Folder) m, vimType.getSimpleName(), new ArrayList<ManagedEntity>());
				}
				else if (m.getMOR().getType().equals("ClusterComputeResource") && flatten) {
					ClusterComputeResource cl = (ClusterComputeResource) m;
					mlist = new ArrayList<ManagedEntity>();
					for (HostSystem hs : cl.getHosts()) {
						mlist.add(hs);
					}
				}			
				else {
					mlist = new ArrayList<ManagedEntity>();
					mlist.add(m);
				}
				
				for (ManagedEntity me : mlist) {
					
					if (position >= start && me.getMOR().getType().equals(vimType.getSimpleName())) {

						Class<?> vimClass = Class.forName(vimType.getName());
						Class<?> restClass = Class.forName(restType.getName());
						Object mo = restClass.newInstance();

						// create parameter/argument array and init the rest class
						Class<?> params[] = { vimClass, String.class, String.class };
						Object args[] = { vimClass.cast(me), thisUri, fieldStr };
						Method method = restClass.getMethod("init", params);
						method.invoke(mo, args);

						if (sp.Match(mo)) {
							moList.add(mo);
							count++;
							if (count >= results) {
								break;
							}
						}
					}
				}
				position++;
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (moList.size() != 0) {
			return moList;
		}
		else {
			return null;
		}
	}
}
