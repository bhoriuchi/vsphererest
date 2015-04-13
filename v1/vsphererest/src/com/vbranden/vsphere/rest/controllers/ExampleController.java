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

package com.vbranden.vsphere.rest.controllers;

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


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
			o = this.getExampleEx(vimModelPackage + "." + objectType, vimModelPackage);
		}

		if (o != null) {
			return Response.ok().entity(o).build();
		} else {
			return Response.status(404).build();
		}

	}

	// recursive function to set sample data for each value
	private Object getExampleEx(String className, String vimModelPackage) {

		Object o = null;
		Class<?> t = null;
		boolean found = false;

		try {
			
			// get the object type
			Reflections reflections = new Reflections(vimModelPackage);
			Set<Class<? extends DynamicData>> allClasses = reflections
					.getSubTypesOf(DynamicData.class);

			// loop through each of the classes
			for (Class<?> c : allClasses) {
				
				if (c.getName().toLowerCase()
						.equals(className.toLowerCase())) {

					found = true;
					
					// get all the fields in the class
					o = c.newInstance();
					Field[] fields = o.getClass().getDeclaredFields();

					for (Field field : fields) {

						Class<?> declaredType = field.getType();
						boolean isArray = false;

						// set up the type and determine if there is an array
						if (declaredType.isArray()) {
							t = declaredType.getComponentType();
							isArray = true;
						} 
						else {
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
							
							//System.out.println("Looking for " + field.getName()  + " of type " + t.getName());
							if (isArray) {

								Object ob = this.getExampleEx(t.getName(),
										vimModelPackage);
								Object a = Array.newInstance(t, 2);
								Array.set(a, 0, ob);
								Array.set(a, 1, ob);
								field.set(o, a);

							} else {
								
								field.set(o, this.getExampleEx(t.getName(),
										vimModelPackage));

							}
						}
					}
				}
			}
			
			// print type if not found
			if (!found) {

				System.out.println("Cant find " + className);
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
