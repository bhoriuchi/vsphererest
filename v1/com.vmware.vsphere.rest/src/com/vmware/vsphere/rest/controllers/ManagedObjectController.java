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

import com.vmware.vsphere.rest.models.RESTManagedObject;
import com.vmware.vsphere.rest.models.RESTRequestBody;
import com.hubspot.jackson.jaxrs.PropertyFiltering;

/*
 * Generic Object type controller. This will only work for objects that do not have 
 * fields that point to a managed object reference as MORs will throw an infinite
 * recursion fault most of the time
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
			@QueryParam("fields") String fields) {

		System.out.println("getAll");

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

					// create parameter/argument array
					Class<?> params[] = { String.class, HttpHeaders.class,
							String.class, String.class, String.class,
							int.class, int.class, int.class };
					Object args[] = { viServer, headers, search, fieldStr,
							thisUri, start, position, results };

					// create a new instance of the object and call its getAll
					// method
					Object o = c.newInstance();
					Method m = c.getMethod("getAll", params);
					Object moList = m.invoke(o, args);

					// if the response is not null build an ok response
					if (moList != null) {
						return Response.ok().entity(moList).build();
					} else {
						return null;
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Get a single RESTManagedObject by id
	 */
	@Path("{id}")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntityById(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("id") String id, @QueryParam("fields") String fields) {

		System.out.println("getById");

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
		if (fields != null) {
			fieldStr = fields;
		}

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

					// create parameter/argument array
					Class<?> params[] = { String.class, HttpHeaders.class,
							String.class, String.class, String.class };
					Object args[] = { viServer, headers, fieldStr, thisUri, id };

					// create a new instance of the object and call its getById
					// method
					Object o = c.newInstance();
					Method m = c.getMethod("getById", params);
					Object moList = m.invoke(o, args);

					// if the response is not null build an ok response
					if (moList != null) {
						return Response.ok().entity(moList).build();
					} else {
						return null;
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Get all child RESTManagedObjects of a specific type
	 */
	@Path("{id}/{childType}s")
	@GET
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType,
			@PathParam("childType") String childType,
			@PathParam("id") String id,
			@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("results") int results,
			@DefaultValue("") @QueryParam("search") String search,
			@QueryParam("fields") String fields) {

		System.out.println("getChildren");

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

					// create parameter/argument array
					Class<?> params[] = { String.class, HttpHeaders.class,
							String.class, String.class, String.class, String.class,
							String.class, int.class, int.class, int.class };
					Object args[] = { viServer, headers, search, fieldStr, thisUri, id,
							childType, start, position, results };

					// create a new instance of the object and call its getAll
					// method
					Object o = c.newInstance();
					Method m = c.getMethod("getChildren", params);
					Object moList = m.invoke(o, args);

					// if the response is not null build an ok response
					if (moList != null) {
						return Response.ok().entity(moList).build();
					} else {
						return null;
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Create a new RESTManagedObject
	 */
	@POST
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEntity(@Context HttpHeaders headers,
			@PathParam("viServer") String viServer,
			@PathParam("objectType") String objectType, RESTRequestBody body) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fields = defaults;

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

					// create parameter/argument array
					Class<?> params[] = { String.class, HttpHeaders.class,
							String.class, String.class, RESTRequestBody.class };
					Object args[] = { viServer, headers, fields, thisUri, body };

					// create a new instance of the object and call its create
					// method
					Object o = c.newInstance();
					Method m = c.getMethod("create", params);
					Object r = m.invoke(o, args);

					// if the response is not null build an ok response
					if (r != null) {
						return (Response) r;
					} else {
						return null;
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Modify a RESTManagedObject
	 */
	@Path("{id}")
	@PUT
	@PropertyFiltering(using = "fields", defaults = "id,name,responseStatus,responseMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putEntity(@Context HttpHeaders headers,
			@PathParam("id") String id,
			@PathParam("objectType") String objectType,
			@PathParam("viServer") String viServer, RESTRequestBody body) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;
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

					// create parameter/argument array
					Class<?> params[] = { String.class, HttpHeaders.class,
							String.class, String.class, String.class,
							RESTRequestBody.class };
					Object args[] = { viServer, headers, fieldStr, thisUri, id,
							body };

					// create a new instance of the object and call its update
					// method
					Object o = c.newInstance();
					Method m = c.getMethod("update", params);
					Object r = m.invoke(o, args);

					// if the response is not null build an ok response
					if (r != null) {
						return (Response) r;
					} else {
						return null;
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Remove a RESTManagedObject
	 */
	@Path("{id}")
	@DELETE
	@PropertyFiltering(using = "fields", defaults = defaults)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteEntity(@Context HttpHeaders headers,
			@PathParam("id") String id,
			@PathParam("objectType") String objectType,
			@PathParam("viServer") String viServer) {

		// initialize variables
		String thisUri = uri.getBaseUri().toString() + viServer + "/";
		String fieldStr = defaults;

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

					// create parameter/argument array
					Class<?> params[] = { String.class, HttpHeaders.class,
							String.class, String.class, String.class };
					Object args[] = { viServer, headers, fieldStr, thisUri, id };

					// create a new instance of the object and call its remove
					// method
					Object o = c.newInstance();
					Method m = c.getMethod("remove", params);
					Object r = m.invoke(o, args);

					// if the response is not null build an ok response
					if (r != null) {
						return (Response) r;
					} else {
						return null;
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
