package com.vmware.vsphere.rest.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.vmware.vsphere.rest.models.v5.RESTCustomResponse;

public class ObjectGetter {

	private Object obj;
	private boolean failed;

	public ObjectGetter() {
	}

	public ObjectGetter getEntity(boolean condition, String type, String id,
			ViConnection viConnection, RESTCustomResponse cr) {

		this.setFailed(false);

		if (condition) {

			// look for type and id
			if (type == null) {
				this.setFailed(true);
				cr.getResponseMessage().add("No type specified");
			}
			if (id == null) {
				this.setFailed(true);
				cr.getResponseMessage().add("No id specified");
			}

			// if type and id were specified then this should be enough to
			// find the managed entity
			if (!this.isFailed()) {

				// search for the entity
				Object o = viConnection.getEntity(type, id);

				// if the entity is not null set it
				if (o != null) {
					this.setObj(o);
				}

				// otherwise set the failed flag and add a response message
				else {
					this.setFailed(true);
					cr.getResponseMessage().add(
							"Could not find " + type + "-" + id);
				}
			}
		} else {
			this.setFailed(true);
		}

		// if isFailed set the object response to a custom response
		if (this.isFailed()) {
			cr.setResponseStatus("failed");
			this.setObj(cr);
		}

		return this;
	}

	public ObjectGetter invoke(boolean condition, Object o, Class<?> c,
			String method, Object value, Class<?> valueType,
			ViConnection viConnection, RESTCustomResponse cr) {

		this.setFailed(false);

		if (condition) {
			
			if (valueType == String.class && value == null) {
				this.setFailed(true);
				cr.getResponseMessage().add("No value specified for " + method);
			}
			else if (valueType == int.class && (int)value == -1) {
				this.setFailed(true);
				cr.getResponseMessage().add("No value specified for " + method);	
			}

			
			if (!this.isFailed()) {
				Class<?> params[] = { valueType };
				Object args[] = { value };
	
				try {
					
					Method m = c.getMethod(method, params);
					m.invoke(o, args);
					this.setObj(o);
	
				} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.setFailed(true);
					cr.getResponseMessage().add("Could not " + method);
				} 
			}

		} else {
			this.setFailed(true);
		}

		// if isFailed set the object response to a custom response
		if (this.isFailed()) {
			cr.setResponseStatus("failed");
			this.setObj(cr);
		}

		return this;

	}

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj
	 *            the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * @return the failFlag
	 */
	public boolean isFailed() {
		return failed;
	}

	/**
	 * @param failFlag
	 *            the failFlag to set
	 */
	public void setFailed(boolean failed) {
		this.failed = failed;
	}

}
