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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class SearchParser {

	private String field;
	private String operator;
	private String value;
	private String search;

	public SearchParser() {
	}

	public SearchParser(String search) {

		this.search = search;
		ParseSearch();
	}

	public void ParseSearch() {

		try {

			String search = URLDecoder.decode(this.search, "UTF-8");

			/*
			 * The search string is passed in from the querystring search
			 * searches take the form <path.to.field>-<search type>(<search
			 * value>) for example search=guest.net.network-matches(10.1.1)
			 * valid operators are equals, notequals, matches, notmatches
			 */

			if (search.indexOf("-") != -1 && search.indexOf("(") != -1
					&& search.indexOf(")") != -1) {
				this.setField(search.substring(0, search.indexOf("-")));
				this.setOperator(search.substring(search.indexOf("-") + 1,
						search.indexOf("(")));
				this.setValue(search.substring(search.indexOf("(") + 1,
						search.indexOf(")")));
			} else {
				this.setField("");
				this.setOperator("");
				this.setValue("");
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// verify all fields
	public Boolean isValid() {

		if (!this.field.equals("") && !this.operator.equals("")
				&& !this.value.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	// helper function to compile a list of all inherited fields
	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			fields = getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

	// helper function to find a specific field
	public static Field getAnyField(Class<?> cl, String field) {
		try {

			for (Field f : getAllFields(new LinkedList<Field>(), cl)) {
				if (f.getName().toLowerCase().equals(field.toLowerCase())) {
					return f;
				}
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// helper function to drill down into an object
	public Object getObject(Object object, String field) {

		try {

			Field f = getAnyField(object.getClass(), field);
			f.setAccessible(true);

			// check if the field is accessible and return it
			if (f.isAccessible()) {
				return f.get(object);
			}

		} catch (NullPointerException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Couldnt get field " + field);
		}

		return null;
	}

	// match function that matches the entire object path
	public Boolean Match(Object object) {
		return Match(object, this.field);
	}

	// main match function that can drill down into subclasses
	public Boolean Match(Object o, String fieldStr) {

		if (fieldStr == null || fieldStr.equals("")) {
			return true;
		}

		String[] fields = fieldStr.split("\\.");
		String bc = "";

		// loop through the fields
		for (String field : fields) {

			// update the breadcrumb to the remaining fields
			if (fields.length > 1) {
				bc = fieldStr.substring(bc.length() + field.length() + 1,
						fieldStr.length());
			}

			o = this.getObject(o, field);

			if (o.getClass().isArray()) {
				for (Object obj : (Object[]) o) {
					if (this.Match(obj, bc)) {
						return true;
					}
				}
				return false;
			}
		}

		// if the final instance is a string then we can evaluate it
		if (o instanceof String) {
			String value = (String) o;
			return this.evaluate(value);
		}

		return false;

	}

	// evaluate the value and compare value based on the operator
	private boolean evaluate(String value) {

		// start matching
		if (this.operator.toLowerCase().equals("equals")
				&& value.equals(this.value)) {
			return true;
		} else if (this.operator.equals("notequals")
				&& !value.equals(this.value)) {
			return true;
		} else if (this.operator.toLowerCase().equals("matches")
				&& value.toLowerCase().contains(this.value.toLowerCase())) {
			return true;
		} else if (this.operator.toLowerCase().equals("notmatches")
				&& !value.toLowerCase().contains(this.value.toLowerCase())) {
			return true;
		}

		return false;

	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search
	 *            the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

}
