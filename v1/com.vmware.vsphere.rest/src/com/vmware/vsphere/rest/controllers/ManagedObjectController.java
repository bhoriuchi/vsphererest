package com.vmware.vsphere.rest.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.reflections.Reflections;

import com.vmware.vsphere.rest.models.v5.RESTCustomResponse;
import com.vmware.vsphere.rest.models.v5.RESTManagedObject;
import com.vmware.vsphere.rest.models.v5.RESTRequestBody;
import com.hubspot.jackson.jaxrs.PropertyFiltering;

/*
 * Generic Object type controller
 */
@Path("/{viServer}/{objectType}s")
public class ManagedObjectController {

	// default values
	private int maxResults = 100;
	final static String defaults = "id,name,resource,responseStatus,responseMessage";
	final static String restPrefix = "REST";

	// API version models
	final static String vim25ModelPackage = "com.vmware.vim25.mo";
	final static String defaultVimModelPackage = vim25ModelPackage;

	final static String v5ModelPackage = "com.vmware.vsphere.rest.models.v5";
	final static String defaultModelPackage = v5ModelPackage;

	// initialize
	@Context
	UriInfo uri;

	/*
	 * Get all RESTManagedObjects
	 */
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("results") int results,
			@DefaultValue("") @QueryParam("search") String search,
			@QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion) {

		return this.getAllEx(viServer, headers, sessionKey, apiVersion, fields,
				search, objectType, start, results);
	}

	/*
	 * Get a single RESTManagedObject by id
	 */
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id,
			@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("results") int results,
			@DefaultValue("") @QueryParam("search") String search,
			@QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion) {

		return this.getEntityByIdEx(viServer, headers, sessionKey, apiVersion,
				id, fields, search, objectType, null, start, results);
	}

	/*
	 * Get the children of a RESTManagedObject
	 */
	@Path("{id}/{childType}s")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildrenById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id,
			@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("results") int results,
			@DefaultValue("") @QueryParam("search") String search,
			@QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion) {

		return this.getEntityByIdEx(viServer, headers, sessionKey, apiVersion,
				id, fields, search, objectType, childType, start, results);
	}

	/*
	 * Create a new RESTManagedObject
	 */
	@POST
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id, @QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion, RESTRequestBody body) {

		return this.postEntityEx(viServer, headers, sessionKey, apiVersion,
				null, fields, objectType, null, body);
	}

	/*
	 * Create a new RESTManagedObject child
	 */
	@Path("{id}/{childType}s")
	@POST
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postChild(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id, @QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion, RESTRequestBody body) {

		return this.postEntityEx(viServer, headers, sessionKey, apiVersion, id,
				fields, objectType, childType, body);
	}

	/*
	 * Modify a RESTManagedObject
	 */
	@Path("{id}")
	@PUT
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id, @QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion, RESTRequestBody body) {

		return this.putEntityEx(viServer, headers, sessionKey, apiVersion, id,
				fields, objectType, childType, body);
	}

	/*
	 * Remove a RESTManagedObject
	 */
	@Path("{id}")
	@DELETE
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("id") String id, @QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion) {

		return this.removeEntityEx(viServer, headers, sessionKey, apiVersion,
				id, fields, objectType);

	}

	/*
	 * function that gets all entities
	 */
	private Response getAllEx(String viServer, HttpHeaders headers,
			String sessionKey, String apiVersion, String fields, String search,
			String objectType, int start, int results) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		int position = 0;
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		// determine the result set
		if (results > this.maxResults) {
			results = this.maxResults;
		}

		Class<?> params[] = { String.class, String.class, String.class,
				String.class, HttpHeaders.class, String.class, String.class,
				String.class, String.class, int.class, int.class, int.class };
		Object args[] = { "vimType", "vimClass", "restClass", viServer,
				headers, sessionKey, search, fieldStr, thisUri, start,
				position, results };

		// call the getAll function
		Object r = this.callMethodByName(objectType, "getAll", params, args,
				apiVersion);

		// return the results
		if (r != null) {
			return (Response) r;
		}

		return null;
	}

	/*
	 * function that gets entities by their id as well as child entities
	 */
	private Response getEntityByIdEx(String viServer, HttpHeaders headers,
			String sessionKey, String apiVersion, String id, String fields,
			String search, String objectType, String childType, int start,
			int results) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		int position = 0;
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		// determine the result set
		if (results > this.maxResults) {
			results = this.maxResults;
		}

		Class<?> params[] = { String.class, String.class, String.class,
				String.class, HttpHeaders.class, String.class, String.class,
				String.class, String.class };
		Object args[] = { "vimType", "vimClass", "restClass", viServer,
				headers, sessionKey, fieldStr, thisUri, id };

		// get the object by id
		Object mo = this.callMethodByName(objectType, "getById", params, args,
				apiVersion);

		// typecast the object
		Response r = (Response) mo;

		// if the request was for a child type, try to get the
		// children
		if (childType != null && r.getStatus() == 200) {

			// set params/args for getChildren method
			Class<?> childParams[] = { String.class, String.class,
					String.class, String.class, HttpHeaders.class,
					String.class, String.class, String.class, String.class,
					String.class, String.class, int.class, int.class, int.class };
			Object childArgs[] = { "vimType", "vimClass", "restClass",
					viServer, headers, sessionKey, search, fieldStr, thisUri,
					id, childType, start, position, results };

			// get the children
			Object cr = this.callMethodByName(objectType, "getChildren",
					childParams, childArgs, apiVersion);

			// if the response is not null build an ok response
			return (Response) cr;

		} else {
			return r;
		}
	}

	/*
	 * function to create a new entity
	 */
	private Response postEntityEx(String viServer, HttpHeaders headers,
			String sessionKey, String apiVersion, String id, String fields,
			String objectType, String childType, RESTRequestBody body) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		if (id != null && childType != null) {

			// create parameter/argument array
			Class<?> params[] = { String.class, String.class, String.class,
					String.class, HttpHeaders.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class, RESTRequestBody.class };
			Object args[] = { "vimType", "vimClass", "restClass", viServer,
					headers, sessionKey, apiVersion, fieldStr, thisUri, id,
					childType, body };

			// call the create method
			Object r = this.callMethodByName(objectType, "createChild", params,
					args, apiVersion);
			if (r != null) {
				return (Response) r;
			}
		}

		else {

			// create parameter/argument array
			Class<?> params[] = { String.class, String.class, String.class,
					String.class, HttpHeaders.class, String.class,
					String.class, String.class, RESTRequestBody.class };
			Object args[] = { "vimType", "vimClass", "restClass", viServer,
					headers, sessionKey, fieldStr, thisUri, body };

			// call the create method
			Object r = this.callMethodByName(objectType, "create", params,
					args, apiVersion);
			if (r != null) {
				return (Response) r;
			}
		}

		return null;
	}

	/*
	 * function to update an entitiy
	 */
	private Response putEntityEx(String viServer, HttpHeaders headers,
			String sessionKey, String apiVersion, String id, String fields,
			String objectType, String childType, RESTRequestBody body) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		Class<?> params[] = { String.class, String.class, String.class,
				String.class, HttpHeaders.class, String.class, String.class,
				String.class, String.class, RESTRequestBody.class };
		Object args[] = { "vimType", "vimClass", "restClass", viServer,
				headers, sessionKey, fieldStr, thisUri, id, body };

		// call the create method
		Object r = this.callMethodByName(objectType, "update", params, args,
				apiVersion);
		if (r != null) {
			return (Response) r;
		}

		return null;

	}

	/*
	 * remove an entity
	 */
	private Response removeEntityEx(String viServer, HttpHeaders headers,
			String sessionKey, String apiVersion, String id, String fields,
			String objectType) {
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		// create parameter/argument array
		Class<?> params[] = { String.class, String.class, String.class,
				String.class, HttpHeaders.class, String.class, String.class,
				String.class, String.class };
		Object args[] = { "vimType", "vimClass", "restClass", viServer,
				headers, sessionKey, fieldStr, thisUri, id };

		// call the create method
		Object r = this.callMethodByName(objectType, "remove", params, args,
				apiVersion);
		if (r != null) {
			return (Response) r;
		}

		return null;
	}

	/*
	 * call a method by its name with parameters using reflections
	 */
	private Object callMethodByName(String objectType, String methodName,
			Class<?> params[], Object args[], String apiVersion) {

		// initialize a custom response
		RESTCustomResponse cr = new RESTCustomResponse("",
				new ArrayList<String>());

		try {

			// default model packages. always default to the original version to
			// avoid breaking existing calls that have no version specified
			String modelPackage = defaultModelPackage;
			String vimModelPackage = defaultVimModelPackage;

			/*
			 * versioning is done by selecting class objects from a specific
			 * package version
			 */

			// version 5 is the first and indicates compatibility with vSphere 5
			if (apiVersion == "5") {
				modelPackage = v5ModelPackage;
				vimModelPackage = vim25ModelPackage;
			}

			// get all classes from com.vmware.vsphere.rest.models
			Reflections reflections = new Reflections(modelPackage);
			Set<Class<? extends RESTManagedObject>> allClasses = reflections
					.getSubTypesOf(RESTManagedObject.class);

			// loop through each of the classes
			for (Class<?> c : allClasses) {
				// if a class with the name REST<objectType> is found then
				// attempt to use that
				if (c.getSimpleName()
						.toLowerCase()
						.equals(restPrefix.toLowerCase()
								+ objectType.toLowerCase())) {

					// set the vim class name
					String vimType = c.getSimpleName().substring(
							restPrefix.length(), c.getSimpleName().length());

					// set values for the classes
					args[0] = vimType;
					args[1] = vimModelPackage + "." + vimType;
					args[2] = c.getName();

					// create a new instance of the object and call its getById
					// method
					Object o = c.newInstance();
					Method m = c.getMethod(methodName, params);

					return m.invoke(o, args);
				}
			}
			return Response.status(404).build();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {

			cr.setResponseStatus("failed");
			cr.getResponseMessage().add(
					"This method is not allowed for the current object type");

			return Response.status(405).entity(cr).build();
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
