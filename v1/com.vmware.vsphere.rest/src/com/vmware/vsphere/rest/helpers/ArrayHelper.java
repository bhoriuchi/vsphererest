package com.vmware.vsphere.rest.helpers;

import java.util.List;

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
}
