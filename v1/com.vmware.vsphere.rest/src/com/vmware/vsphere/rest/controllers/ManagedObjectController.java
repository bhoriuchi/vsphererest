package com.vmware.vsphere.rest.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import com.vmware.vsphere.rest.models.RESTCustomResponse;
import com.vmware.vsphere.rest.models.RESTManagedObject;
import com.vmware.vsphere.rest.models.RESTRequestBody;
import com.hubspot.jackson.jaxrs.PropertyFiltering;

/*
 * Generic Object type controller
 */
@Path("/{viServer}/{objectType}s")
public class ManagedObjectController {

	// default values
	private int maxResults = 100;
	final static String defaults = "id,name,responseStatus,responseMessage";

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

		return this.getAllEx(viServer, headers, sessionKey, apiVersion, fields, search, objectType,
				start, results);
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

		return this.getEntityByIdEx(viServer, headers, sessionKey, apiVersion, id, fields, search,
				objectType, null, start, results);
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

		return this.getEntityByIdEx(viServer, headers, sessionKey, apiVersion, id, fields, search,
				objectType, childType, start, results);
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
			@QueryParam("v") String apiVersion,
			RESTRequestBody body) {

		return this.postEntityEx(viServer, headers, sessionKey, apiVersion, null, fields, objectType,
				null, body);
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
			@PathParam("id") String id, 
			@QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion,
			RESTRequestBody body) {

		return this.postEntityEx(viServer, headers, sessionKey, apiVersion, id, fields, objectType,
				childType, body);
	}

	/*
	 * Modify a RESTManagedObject
	 */
	@Path("{id}")
	@PUT
	@PropertyFiltering(using = "fields", defaults = "id,name,responseStatus,responseMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id, 
			@QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion,
			RESTRequestBody body) {

		return this.putEntityEx(viServer, headers, sessionKey, apiVersion, id, fields, objectType, childType, body);
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
			@PathParam("id") String id,
			@QueryParam("fields") String fields,
			@QueryParam("sessionkey") String sessionKey,
			@QueryParam("v") String apiVersion) {

		return this.removeEntityEx(viServer, headers, sessionKey, apiVersion, id, fields, objectType);

	}
	
	

	

	// private functions. these have been separated out for versioning and to limit the amount of redundant code

	/*
	 * function that gets all entities
	 */
	private Response getAllEx(String viServer, HttpHeaders headers, String sessionKey, String apiVersion,
			String fields, String search, String objectType, int start,
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

		Class<?> params[] = { String.class, HttpHeaders.class, String.class, String.class, String.class,
				String.class, String.class, int.class, int.class, int.class };
		Object args[] = { viServer, headers, sessionKey, apiVersion, search, fieldStr, thisUri, start,
				position, results };

		// call the getAll function
		Object moList = this.callMethodByName(objectType, "getAll", params,
				args);

		// return the results
		if (moList != null) {
			return Response.ok().entity(moList).build();
		}

		return null;
	}

	/*
	 * function that gets entities by their id as well as child entities
	 */
	private Response getEntityByIdEx(String viServer, HttpHeaders headers, String sessionKey, String apiVersion,
			String id, String fields, String search, String objectType,
			String childType, int start, int results) {

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

		Class<?> params[] = { String.class, HttpHeaders.class, String.class, String.class, String.class,
				String.class, String.class };
		Object args[] = { viServer, headers, sessionKey, apiVersion, fieldStr, thisUri, id };

		// get the object by id
		Object mo = this.callMethodByName(objectType, "getById", params, args);

		// if the response is not null build an ok response
		if (mo != null) {

			// if the request was for a child type, try to get the
			// children
			if (childType != null) {

				// set params/args for getChildren method
				Class<?> childParams[] = { String.class, HttpHeaders.class, String.class, String.class,
						String.class, String.class, String.class, String.class,
						String.class, int.class, int.class, int.class };
				Object childArgs[] = { viServer, headers, sessionKey, apiVersion, search, fieldStr,
						thisUri, id, childType, start, position, results };

				// get the children
				Object moList = this.callMethodByName(objectType,
						"getChildren", childParams, childArgs);

				// if the response is not null build an ok response
				if (moList != null) {
					return Response.ok().entity(moList).build();
				}
			} else {
				return Response.ok().entity(mo).build();
			}
		}
		return null;
	}

	/*
	 * function to create a new entity
	 */
	private Response postEntityEx(String viServer, HttpHeaders headers, String sessionKey, String apiVersion,
			String id, String fields, String objectType, String childType,
			RESTRequestBody body) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		if (id != null && childType != null) {

			// create parameter/argument array
			Class<?> params[] = { String.class, HttpHeaders.class, String.class, String.class,
					String.class, String.class, String.class, String.class,
					RESTRequestBody.class };
			Object args[] = { viServer, headers, sessionKey, apiVersion, fieldStr, thisUri, id,
					childType, body };

			// call the create method
			Object r = this.callMethodByName(objectType, "createChild", params,
					args);
			if (r != null) {
				return (Response) r;
			}
		}

		else {

			// create parameter/argument array
			Class<?> params[] = { String.class, HttpHeaders.class, String.class, String.class,
					String.class, String.class, RESTRequestBody.class };
			Object args[] = { viServer, headers, sessionKey, apiVersion, fieldStr, thisUri, body };

			// call the create method
			Object r = this
					.callMethodByName(objectType, "create", params, args);
			if (r != null) {
				return (Response) r;
			}
		}

		return null;
	}
	
	/*
	 * function to update an entitiy
	 */
	private Response putEntityEx(String viServer, HttpHeaders headers, String sessionKey, String apiVersion,
			String id, String fields, String objectType, String childType,
			RESTRequestBody body) {
		
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}
		
		Class<?> params[] = { String.class, HttpHeaders.class, String.class, String.class,
				String.class, String.class, String.class,
				RESTRequestBody.class };
		Object args[] = { viServer, headers, sessionKey, apiVersion, fieldStr, thisUri, id,
				body };
		
		// call the create method
		Object r = this.callMethodByName(objectType, "update", params,
				args);
		if (r != null) {
			return (Response) r;
		}

		return null;
		
	}

	
	
	
	/*
	 * remove an entity
	 */
	private Response removeEntityEx(String viServer, HttpHeaders headers, String sessionKey, String apiVersion,
			String id, String fields, String objectType) {
		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

		// create parameter/argument array
		Class<?> params[] = { String.class, HttpHeaders.class, String.class, String.class,
				String.class, String.class, String.class };
		Object args[] = { viServer, headers, sessionKey, apiVersion, fieldStr, thisUri, id };
		
		// call the create method
		Object r = this.callMethodByName(objectType, "remove", params,
				args);
		if (r != null) {
			return (Response) r;
		}
		
		return null;
	}
	
	/*
	 * call a method by its name with parameters using reflections
	 */
	private Object callMethodByName(String objectType, String methodName,
			Class<?> params[], Object args[]) {
		try {

			
			// get all classes from com.vmware.vsphere.rest.models
			Reflections reflections = new Reflections(
					"com.vmware.vsphere.rest.models");
			Set<Class<? extends RESTManagedObject>> allClasses = reflections
					.getSubTypesOf(RESTManagedObject.class);

			// loop through each of the classes
			for (Class<?> c : allClasses) {

				// if a class with the name REST<objectType> is found then
				// attempt to use that
				if (c.getSimpleName().toLowerCase()
						.equals("rest" + objectType.toLowerCase())) {

					// create a new instance of the object and call its getById
					// method
					Object o = c.newInstance();
					Method m = c.getMethod(methodName, params);
					return m.invoke(o, args);
				}
			}

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
			return Response
					.status(405)
					.entity(new RESTCustomResponse("notAllowed",
							"this method is not allowed for the current object type")).build();
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

	/*
	 * @Path("{id}")
	 * 
	 * @GET
	 * 
	 * @PropertyFiltering(using = "fields", defaults = defaults)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public ManagedObject
	 * getEntityById(@Context HttpHeaders headers,
	 * 
	 * @PathParam("viServer") String viServer, @PathParam("objectType") String
	 * objectType,
	 * 
	 * @PathParam("id") String id, @QueryParam("fields") String fields) {
	 * 
	 * try {
	 * 
	 * Reflections reflections = new Reflections("com.vmware.vim25.mo");
	 * Set<Class<? extends ManagedObject>> allClasses =
	 * reflections.getSubTypesOf(ManagedObject.class);
	 * 
	 * for (Class<?> c : allClasses) { System.out.println(c.getSimpleName()); if
	 * (c.getSimpleName().toLowerCase().equals(objectType.toLowerCase())) {
	 * 
	 * // Create a new MOR and use the MorUtil to create the object
	 * ManagedObjectReference mor = new ManagedObjectReference();
	 * mor.setType(c.getSimpleName()); mor.setVal(id); ManagedObject mo =
	 * MorUtil.createExactManagedObject(new
	 * ViConnection().getServiceInstance(headers,
	 * viServer).getServerConnection(), mor);
	 * 
	 * if (mo != null) { return mo; } else { return null; } } }
	 * 
	 * 
	 * } catch (NullPointerException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return null; }
	 */

}
