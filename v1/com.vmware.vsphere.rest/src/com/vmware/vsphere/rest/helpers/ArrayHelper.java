package com.vmware.vsphere.rest.helpers;

import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.NumericRange;

public class ArrayHelper {

	public ArrayHelper() {
	}

	public boolean containsCaseInsensitive(String s, List<String> l) {
		if (l != null && s != null) {
			for (String string : l) {
				if (string.equalsIgnoreCase(s)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsCaseInsensitive(String s, String[] l) {
		if (l != null && s != null) {
			for (String string : l) {
				if (string.equalsIgnoreCase(s)) {
					return true;
				}
			}
		}
		return false;
	}

	public String containsCaseInsensitiveGet(String s, String[] l) {

		if (l != null && s != null) {
			for (String string : l) {
				if (string.equalsIgnoreCase(s)) {
					return string;
				}
			}
		}
		return null;
	}
	
	public NumericRange[] getNumericRange(String input) {
		int min = 0;
		int max = 4094;
		int start = 0;
		int end = 0;
		
		List<NumericRange> l = new ArrayList<NumericRange>();
		
		// remove spaces and split the string at the commas
		String[] ranges = input.replaceAll("\\s", "").split(",");
		
		// look through the ranges
		for (String range : ranges) {
			
			// split each range into a start and end
			String[] r = range.split("-");
			
			// a range can either be 1 number or 2 separated by a -
			if (r.length == 1) {
				start = Integer.parseInt(r[0]);
				end = start;
			}
			else if (r.length == 2) {
				start = Integer.parseInt(r[0]);
				end = Integer.parseInt(r[1]);
			}
			else {
				continue;
			}
			
			// add the new range if it is within the valid limit
			if (start >= min && start <= max && end >= min && end <= max) {
				NumericRange t = new NumericRange();
				t.setStart(start);
				t.setEnd(end);
				l.add(t);
			}
		}
		
		return l.toArray(new NumericRange[l.size()]);
	}
	
}
