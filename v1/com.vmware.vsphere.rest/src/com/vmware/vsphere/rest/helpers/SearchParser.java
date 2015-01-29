package com.vmware.vsphere.rest.helpers;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

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
			 * searches take the form <path.to.field>-<search type>(<search value>)
			 * for example search=guest.net.network-matches(10.1.1)
			 * valid operators are equals, notequals, matches, notmatches
			 */
			
			if (search.indexOf("-") != -1 && search.indexOf("(") != -1
					&& search.indexOf(")") != -1) {
				this.field = search.substring(0, search.indexOf("-"));
				this.operator = search.substring(search.indexOf("-") + 1,
						search.indexOf("("));
				this.value = search.substring(search.indexOf("(") + 1,
						search.indexOf(")"));
			} else {
				this.field = "";
				this.operator = "";
				this.value = "";
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

	// helper function to drill down into an object
	public Object getObject(Object object, String field) {

		try {

			Field f = object.getClass().getDeclaredField(field);
			f.setAccessible(true);

			// check if the field is accessible and return it
			if (f.isAccessible()) {
				return f.get(object);
			}

			// if the field is not accessible, loop through its methods and try
			// to find a public method that returns
			// the same type as the variable and then call it
			else {

				for (Method m : object.getClass().getMethods()) {
					if (m.getReturnType() == f.getType()) {
						return m.invoke(object);
					}
				}

			}

		} catch (NullPointerException | NoSuchFieldException
				| SecurityException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// match function that matches the entire object path
	public Boolean Match(Object object) {
		return Match(object, this.field);
	}

	// match function that matches a partial object path
	public Boolean Match(Object object, String fields) {

		if (this.isValid()) {
			try {

				String valueof = "";
				String[] fieldPath = fields.split("\\.");
				int currentPathLength = 0;

				// loop through remaining fields
				for (int i = 0; i < fieldPath.length; i++) {

					// calculate the current position in the path string
					currentPathLength += fieldPath[i].length() + 1;
					object = getObject(object, fieldPath[i]);

					// if the object returned is an array, recursively search it
					if (object instanceof Object[]) {
						for (Object o : (Object[]) object) {
							if (this.Match(
									o,
									fields.substring(currentPathLength,
											fields.length()))) {
								return true;
							}
						}

						// if the recursive search never returned true, return
						// false
						return false;
					}

					if (object == null) {
						return false;
					}
				}

				if (object instanceof String) {
					valueof = (String) object;
				} else {
					return false;
				}

				// start matching
				if (this.operator.toLowerCase().equals("equals")
						&& valueof.equals(this.value)) {
					return true;
				} else if (this.operator.equals("notequals")
						&& !valueof.equals(this.value)) {
					return true;
				} else if (this.operator.toLowerCase().equals("matches")
						&& valueof.toLowerCase().contains(
								this.value.toLowerCase())) {
					return true;
				} else if (this.operator.toLowerCase().equals("notmatches")
						&& !valueof.toLowerCase().contains(
								this.value.toLowerCase())) {
					return true;
				}

			} catch (SecurityException | IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return true;
		}
		return false;
	}

	public String getField() {
		return this.field;
	}

	public String getOperator() {
		return this.operator;
	}

	public String getValue() {
		return this.value;
	}

	public String getSearch() {
		return this.search;
	}
}
