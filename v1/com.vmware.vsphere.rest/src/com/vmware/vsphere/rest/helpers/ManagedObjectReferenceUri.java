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

package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vmware.vim25.ManagedObjectReference;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class ManagedObjectReferenceUri {

	public ManagedObjectReferenceUri() {
	}

	public String getUri(Object o, String uri) {

		String ref = null;
		ManagedObjectReference mor;

		if (o != null) {

			try {
				
				if (o instanceof ManagedObjectReference) {
					mor = (ManagedObjectReference) o;
				}
				else {
					// get the managed object reference
					mor = (ManagedObjectReference) o.getClass().getMethod("getMOR").invoke(o);
				}
				// construct the reference URI. Add an s to the object type
				// to make the REST call plural per best practice
				if (mor != null) {
					ref = uri + mor.getType().toLowerCase() + "s/" + mor.getVal();
				}
				
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ref;
	}
	public String getId(String resource) {
		Pattern p = Pattern.compile("^http?://");
		Matcher m = p.matcher(resource);
		
		if (m.find()) {
			return resource.substring(resource.lastIndexOf('/') + 1, resource.length());
		}
		else {
			return resource;
		}
		
	}
}
