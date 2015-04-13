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

package com.vbranden.vsphere.rest.helpers;

import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.NumericRange;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

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
