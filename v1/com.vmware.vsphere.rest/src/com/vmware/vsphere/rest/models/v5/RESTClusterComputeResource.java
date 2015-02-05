package com.vmware.vsphere.rest.models.v5;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.vmware.vim25.ClusterActionHistory;
import com.vmware.vim25.ClusterConfigInfo;
import com.vmware.vim25.ClusterConfigSpec;
import com.vmware.vim25.ClusterDrsFaults;
import com.vmware.vim25.ClusterDrsMigration;
import com.vmware.vim25.ClusterDrsRecommendation;
import com.vmware.vim25.ClusterRecommendation;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vmware.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vsphere.rest.helpers.FieldGet;
import com.vmware.vsphere.rest.helpers.ViConnection;

public class RESTClusterComputeResource extends RESTComputeResource {

	private ClusterActionHistory[] actionHistory;
	private ClusterConfigInfo configuration;
	private ClusterDrsFaults[] drsFault;
	private ClusterDrsRecommendation[] drsRecommendation;
	private ClusterDrsMigration[] migrationHistory;
	private ClusterRecommendation[] recommendation;

	// constructor
	public RESTClusterComputeResource() {
	}

	// overloaded constructor
	public RESTClusterComputeResource(ClusterComputeResource mo, String uri,
			String fields) {
		this.init(mo, uri, fields);
	}

	public void init(ClusterComputeResource mo, String uri, String fields) {

		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();

		try {

			// specific fields
			if (fg.get("actionHistory", fields)) {
				this.setActionHistory(mo.getActionHistory());
			}
			if (fg.get("configuration", fields)) {
				this.setConfiguration(mo.getConfiguration());
			}
			if (fg.get("drsFault", fields)) {
				this.setDrsFault(mo.getDrsFault());
			}
			if (fg.get("drsRecommendation", fields)) {
				this.setDrsRecommendation(mo.getDrsRecommendation());
			}
			if (fg.get("migrationHistory", fields)) {
				this.setMigrationHistory(mo.getMigrationHistory());
			}
			if (fg.get("recommendation", fields)) {
				this.setRecommendation(mo.getRecommendation());
			}

			// compute resource specific fields
			if (fg.get("configurationEx", fields)) {
				this.setConfigurationEx(mo.getConfigurationEx());
			}
			if (fg.get("datastore", fields)) {
				this.setDatastore(new ManagedObjectReferenceArray()
						.getMORArray(mo.getDatastores(), uri));
			}
			if (fg.get("environmentBrowser", fields)) {

				this.setEnvironmentBrowser(new ManagedObjectReferenceUri()
						.getUri(mo.getEnvironmentBrowser(), uri));
			}
			if (fg.get("host", fields)) {
				this.setHost(new ManagedObjectReferenceArray().getMORArray(
						mo.getHosts(), uri));
			}
			if (fg.get("network", fields)) {
				this.setNetwork(new ManagedObjectReferenceArray().getMORArray(
						mo.getNetworks(), uri));
			}
			if (fg.get("resourcePool", fields)) {
				this.setResourcePool(new ManagedObjectReferenceUri().getUri(
						mo.getResourcePool(), uri));
			}
			if (fg.get("summary", fields)) {
				this.setSummary(mo.getSummary());
			}

			// extended from RESTManagedObject
			if (fg.get("id", fields)) {
				this.setId(mo.getMOR().getVal());
			}
			if (fg.get("moRef", fields)) {
				this.setMoRef(mo.getMOR().getType() + "-"
						+ mo.getMOR().getVal());
			}

			// extended from RESTExtensibleManagedObject
			if (fg.get("availableField", fields)) {
				this.setAvailableField(mo.getAvailableField());
			}
			if (fg.get("value", fields)) {
				this.setValue(mo.getValues());
			}

			// extended from RESTManagedEntity
			if (fg.get("alarmActionsEnabled", fields)) {
				this.setAlarmActionsEnabled(mo.getAlarmActionEabled());
			}
			if (fg.get("configIssue", fields)) {
				this.setConfigIssue(mo.getConfigIssue());
			}
			if (fg.get("configStatus", fields)) {
				this.setConfigStatus(mo.getConfigStatus());
			}
			if (fg.get("customValue", fields)) {
				this.setCustomValue(mo.getCustomValue());
			}
			if (fg.get("declaredAlarmState", fields)) {
				this.setDeclaredAlarmState(mo.getDeclaredAlarmState());
			}
			if (fg.get("disabledMethod", fields)) {
				this.setDisabledMethod(mo.getDisabledMethod());
			}
			if (fg.get("effectiveRole", fields)) {
				this.setEffectiveRole(mo.getEffectiveRole());
			}
			if (fg.get("name", fields)) {
				this.setName(mo.getName());
			}
			if (fg.get("overallStatus", fields)) {
				this.setOverallStatus(mo.getOverallStatus());
			}
			if (fg.get("parent", fields)) {
				this.setParent(new ManagedObjectReferenceUri().getUri(
						mo.getParent(), uri));
			}
			if (fg.get("permission", fields)) {
				this.setPermission(mo.getPermission());
			}
			if (fg.get("recentTask", fields)) {
				this.setRecentTask(new ManagedObjectReferenceArray()
						.getMORArray(mo.getRecentTasks(), uri));
			}
			if (fg.get("tag", fields)) {
				this.setTag(mo.getTag());
			}
			if (fg.get("triggeredAlarmState", fields)) {
				this.setTriggeredAlarmState(mo.getTriggeredAlarmState());
			}

		} catch (RemoteException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * create a new object of this type
	 */
	public Response create(String vimType, String vimClass, String restClass,
			String viServer, HttpHeaders headers, String sessionKey,
			String fields, String thisUri, RESTRequestBody body) {

		try {

			// check for name
			if (body.getName() == null) {
				return Response
						.status(400)
						.entity(new RESTCustomResponse("badRequest",
								"name not specified")).build();
			}
			// check for datacenter
			else if (body.getDatacenter() == null) {
				return Response
						.status(400)
						.entity(new RESTCustomResponse("badRequest",
								"datacenter not specified")).build();
			}

			// create the object
			else {

				// look for the datacenter
				ManagedEntity m = new ViConnection().getEntity("Datacenter",
						body.getDatacenter(), headers, sessionKey, viServer);

				// if the datacenter was found
				if (m != null) {

					// create an empty spec if one doesnt exist
					if (body.getSpec() == null) {
						body.setSpec(new ClusterConfigSpec());
					}

					Datacenter dc = (Datacenter) m;

					ClusterComputeResource mo = dc.getHostFolder()
							.createCluster(body.getName(),
									(ClusterConfigSpec) body.getSpec());
					URI uri = new URI(thisUri
							+ mo.getMOR().getType().toLowerCase() + "s/"
							+ mo.getMOR().getVal());
					return Response
							.created(uri)
							.entity(new RESTClusterComputeResource(mo, thisUri,
									fields)).build();
				}
			}
		} catch (InvalidName e) {
			return Response
					.status(400)
					.entity(new RESTCustomResponse("invalidName", body
							.getName() + " is not a valid name")).build();
		} catch (DuplicateName e) {
			return Response
					.status(400)
					.entity(new RESTCustomResponse("duplicateName", body
							.getName() + " already exists")).build();
		} catch (RuntimeFault e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * update this object
	 */

	/**
	 * @return the actionHistory
	 */
	public ClusterActionHistory[] getActionHistory() {
		return actionHistory;
	}

	/**
	 * @param actionHistory
	 *            the actionHistory to set
	 */
	public void setActionHistory(ClusterActionHistory[] actionHistory) {
		this.actionHistory = actionHistory;
	}

	/**
	 * @return the configuration
	 */
	public ClusterConfigInfo getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(ClusterConfigInfo configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the drsFault
	 */
	public ClusterDrsFaults[] getDrsFault() {
		return drsFault;
	}

	/**
	 * @param drsFault
	 *            the drsFault to set
	 */
	public void setDrsFault(ClusterDrsFaults[] drsFault) {
		this.drsFault = drsFault;
	}

	/**
	 * @return the drsRecommendation
	 */
	public ClusterDrsRecommendation[] getDrsRecommendation() {
		return drsRecommendation;
	}

	/**
	 * @param drsRecommendation
	 *            the drsRecommendation to set
	 */
	public void setDrsRecommendation(
			ClusterDrsRecommendation[] drsRecommendation) {
		this.drsRecommendation = drsRecommendation;
	}

	/**
	 * @return the migrationHistory
	 */
	public ClusterDrsMigration[] getMigrationHistory() {
		return migrationHistory;
	}

	/**
	 * @param migrationHistory
	 *            the migrationHistory to set
	 */
	public void setMigrationHistory(ClusterDrsMigration[] migrationHistory) {
		this.migrationHistory = migrationHistory;
	}

	/**
	 * @return the recommendation
	 */
	public ClusterRecommendation[] getRecommendation() {
		return recommendation;
	}

	/**
	 * @param recommendation
	 *            the recommendation to set
	 */
	public void setRecommendation(ClusterRecommendation[] recommendation) {
		this.recommendation = recommendation;
	}

}