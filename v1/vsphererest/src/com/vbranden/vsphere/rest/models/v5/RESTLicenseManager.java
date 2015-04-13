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

package com.vbranden.vsphere.rest.models.v5;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vbranden.vsphere.rest.helpers.ViConnection;
import com.vmware.vim25.LicenseDiagnostics;
import com.vmware.vim25.LicenseFeatureInfo;
import com.vmware.vim25.LicenseManagerEvaluationInfo;
import com.vmware.vim25.LicenseManagerLicenseInfo;
import com.vmware.vim25.LicenseSource;
import com.vmware.vim25.mo.LicenseManager;


/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTLicenseManager extends RESTManagedObject {

	private LicenseDiagnostics diagnostics;
	private LicenseManagerEvaluationInfo evaluation;
	private LicenseFeatureInfo[] featureInfo;
	private String licenseAssignmentManager;
	private String licensedEdition;
	private LicenseManagerLicenseInfo[] licenses;
	private LicenseSource source;
	private boolean sourceAvailable;
	
	// constructor
	public RESTLicenseManager() {
	}

	// overloaded constructor
	public RESTLicenseManager(ViConnection viConnection, LicenseManager mo, String uri, String fields) {
		this.init(viConnection, mo, uri, fields);
	}

	/*
	 * initialize the object
	 */
	public void init(ViConnection viConnection, LicenseManager mo, String uri, String fields) {
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {

			// specific fields
			if (fg.get("diagnostics", fields)) {
				this.setDiagnostics(null);
			}
			if (fg.get("evaluation", fields)) {
				this.setEvaluation(mo.getEvaluation());
			}
			if (fg.get("featureInfo", fields)) {
				this.setFeatureInfo(null);
			}
			if (fg.get("licenseAssignmentManager", fields)) {
				this.setLicenseAssignmentManager(new ManagedObjectReferenceUri().getUri(mo.getLicenseAssignmentManager(), uri));
			}
			if (fg.get("licensedEdition", fields)) {
				this.setLicensedEdition(null);
			}
			if (fg.get("licenses", fields)) {
				this.setLicenses(mo.getLicenses());
			}
			if (fg.get("source", fields)) {
				this.setSource(null);
			}
			if (fg.get("sourceAvailable", fields)) {
				this.setSourceAvailable(false);
			}
			
			
			// set the extended properties
			this.setManagedObject(viConnection, mo, fields, uri);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the diagnostics
	 */
	public LicenseDiagnostics getDiagnostics() {
		return diagnostics;
	}

	/**
	 * @param diagnostics the diagnostics to set
	 */
	public void setDiagnostics(LicenseDiagnostics diagnostics) {
		this.diagnostics = diagnostics;
	}

	/**
	 * @return the evaluation
	 */
	public LicenseManagerEvaluationInfo getEvaluation() {
		return evaluation;
	}

	/**
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(LicenseManagerEvaluationInfo evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * @return the featureInfo
	 */
	public LicenseFeatureInfo[] getFeatureInfo() {
		return featureInfo;
	}

	/**
	 * @param featureInfo the featureInfo to set
	 */
	public void setFeatureInfo(LicenseFeatureInfo[] featureInfo) {
		this.featureInfo = featureInfo;
	}

	/**
	 * @return the licenseAssignmentManager
	 */
	public String getLicenseAssignmentManager() {
		return licenseAssignmentManager;
	}

	/**
	 * @param licenseAssignmentManager the licenseAssignmentManager to set
	 */
	public void setLicenseAssignmentManager(String licenseAssignmentManager) {
		this.licenseAssignmentManager = licenseAssignmentManager;
	}

	/**
	 * @return the licensedEdition
	 */
	public String getLicensedEdition() {
		return licensedEdition;
	}

	/**
	 * @param licensedEdition the licensedEdition to set
	 */
	public void setLicensedEdition(String licensedEdition) {
		this.licensedEdition = licensedEdition;
	}

	/**
	 * @return the licenses
	 */
	public LicenseManagerLicenseInfo[] getLicenses() {
		return licenses;
	}

	/**
	 * @param licenses the licenses to set
	 */
	public void setLicenses(LicenseManagerLicenseInfo[] licenses) {
		this.licenses = licenses;
	}

	/**
	 * @return the source
	 */
	public LicenseSource getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(LicenseSource source) {
		this.source = source;
	}

	/**
	 * @return the sourceAvailable
	 */
	public boolean isSourceAvailable() {
		return sourceAvailable;
	}

	/**
	 * @param sourceAvailable the sourceAvailable to set
	 */
	public void setSourceAvailable(boolean sourceAvailable) {
		this.sourceAvailable = sourceAvailable;
	}
}
