package com.vmware.vsphere.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import com.hubspot.jackson.jaxrs.PropertyFilteringMessageBodyWriter;

public class vSphereRESTApplication extends Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(PropertyFilteringMessageBodyWriter.class);
        return classes;
    }
}  