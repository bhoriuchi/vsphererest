package com.vmware.vsphere.rest.controllers;

import java.lang.reflect.Field;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.reflections.Reflections;

import com.vmware.vim25.DynamicData;

import java.lang.reflect.Array;

@Path("/example")
public class ExampleController {

	// API version models
	final static String vim25ModelPackage = "com.vmware.vim25";
	final static String defaultVimModelPackage = vim25ModelPackage;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{objectType}")
	public Response getExample(@PathParam("objectType") String objectType,
			@QueryParam("v") String apiVersion) {

		String vimModelPackage = defaultVimModelPackage;

		// version 5 is the first and indicates compatibility with vSphere 5
		if (apiVersion == "5") {
			vimModelPackage = vim25ModelPackage;
		}

		Object o = null;
		
		if (objectType != null) {
			o = this.getExampleEx(objectType, vimModelPackage);
		}

		if (o != null) {
			return Response.ok().entity(o).build();
		} else {
			return Response.status(404).build();
		}

	}

	private Object getExampleEx(String className, String vimModelPackage) {

		Object o = null;
		Class<?> t = null;

		try {
			
			// get the object type
			Reflections reflections = new Reflections(vimModelPackage);
			Set<Class<? extends DynamicData>> allClasses = reflections
					.getSubTypesOf(DynamicData.class);

			// loop through each of the classes
			for (Class<?> c : allClasses) {

				if (c.getSimpleName().toLowerCase()
						.equals(className.toLowerCase())) {
					
					className = c.getSimpleName();

					
					o = c.newInstance();

					
					Field[] fields = o.getClass().getDeclaredFields();

					for (Field field : fields) {

						Class<?> declaredType = field.getType();
						boolean isArray = false;

						// set up the type and determine if there is an array
						if (declaredType.isArray()) {
							t = declaredType.getComponentType();
							isArray = true;
						} else {
							t = declaredType;
						}

						if (t == String.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new String[] { "string1", "string2" });
							} else {
								field.set(o, "stringValue");
							}
						} else if (t == boolean.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new boolean[] { true, false });
							} else {
								field.set(o, false);
							}
						} else if (t == Boolean.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new Boolean[] { true, false });
							} else {
								field.set(o, false);
							}
						} else if (t == int.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new int[] { 11, 23 });
							} else {
								field.set(o, 10);
							}
						} else if (t == Integer.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new Integer[] { 11, 23 });
							} else {
								field.set(o, 10);
							}
						} else if (t == short.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new short[] { (short) 1, (short) 2 });
							} else {
								field.set(o, (short) 1);
							}
						} else if (t == Short.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new Short[] { (short) 1, (short) 2 });
							} else {
								field.set(o, (short) 1);
							}
						} else if (t == long.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new long[] { (long) 12345678910L,
										(long) 123456780L });
							} else {
								field.set(o, (long) 12345678910L);
							}
						} else if (t == Long.class) {
							field.setAccessible(true);

							if (isArray) {
								field.set(o, new Long[] { (long) 12345678910L,
										(long) 123456780L });
							} else {
								field.set(o, (long) 12345678910L);
							}
						} 
						else if (t.isEnum()) {
							
							field.setAccessible(true);
							
							Object[] e = t.getEnumConstants();
							System.out.println("enum:" + e[0]);
							
							if (isArray) {
								
								Object a = Array.newInstance(t, 2);
								Array.set(a, 0, e[0]);
								Array.set(a, 1, e[0]);
								field.set(o, a);
							}
							else {
								field.set(o, e[0]);
							}
						}
						
						else {

							field.setAccessible(true);

							if (isArray) {

								Object ob = this.getExampleEx(t.getSimpleName(),
										vimModelPackage);
								Object a = Array.newInstance(t, 2);
								Array.set(a, 0, ob);
								Array.set(a, 1, ob);
								field.set(o, a);

							} else {
								
								field.set(o, this.getExampleEx(t.getSimpleName(),
										vimModelPackage));

							}
						}
					}	
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("instantiation ");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("illegal ");
		} catch (Exception e) {
			System.out.println("ex ");
		}

		return o;
	}
}
