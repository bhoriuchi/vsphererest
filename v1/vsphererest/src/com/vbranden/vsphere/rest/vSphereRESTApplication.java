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

package com.vbranden.vsphere.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.hubspot.jackson.jaxrs.PropertyFilteringMessageBodyWriter;
import com.vbranden.vsphere.rest.controllers.ExampleController;
import com.vbranden.vsphere.rest.controllers.ManagedObjectController;
import com.vbranden.vsphere.rest.controllers.SessionKeyController;
import com.vbranden.vsphere.rest.controllers.Test;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class vSphereRESTApplication extends Application {
    public Set<Class<?>> getClasses() {
    	
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(CORSResponseFilter.class);
        classes.add(PropertyFilteringMessageBodyWriter.class);
        classes.add(ExampleController.class);
        classes.add(ManagedObjectController.class);
        classes.add(SessionKeyController.class);
        classes.add(Test.class);
        return classes;
    }
}  