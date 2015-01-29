package com.vmware.vsphere.rest.models;

import java.util.List;

import com.vmware.vim25.AlarmState;
import com.vmware.vim25.CustomFieldValue;
import com.vmware.vim25.Event;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.Permission;
import com.vmware.vim25.Tag;

public class RESTManagedEntity extends RESTExtensibleManagedObject {
	
	private boolean alarmActionsEnabled;
	private Event[] configIssue;
	private ManagedEntityStatus configStatus;
	private CustomFieldValue[] customValue;
	private AlarmState[] declaredAlarmState;
	private String[] disabledMethod;
	private int[] effectiveRole;
	private String name;
	private ManagedEntityStatus overallStatus;
	private String parent;
	private Permission[] permission;
	private List<String> recentTask;
	private Tag[] tag;
	private AlarmState[] triggeredAlarmState;
	/**
	 * @return the alarmActionsEnabled
	 */
	public boolean isAlarmActionsEnabled() {
		return alarmActionsEnabled;
	}
	/**
	 * @param alarmActionsEnabled the alarmActionsEnabled to set
	 */
	public void setAlarmActionsEnabled(boolean alarmActionsEnabled) {
		this.alarmActionsEnabled = alarmActionsEnabled;
	}
	/**
	 * @return the configIssue
	 */
	public Event[] getConfigIssue() {
		return configIssue;
	}
	/**
	 * @param configIssue the configIssue to set
	 */
	public void setConfigIssue(Event[] configIssue) {
		this.configIssue = configIssue;
	}
	/**
	 * @return the configStatus
	 */
	public ManagedEntityStatus getConfigStatus() {
		return configStatus;
	}
	/**
	 * @param configStatus the configStatus to set
	 */
	public void setConfigStatus(ManagedEntityStatus configStatus) {
		this.configStatus = configStatus;
	}
	/**
	 * @return the customValue
	 */
	public CustomFieldValue[] getCustomValue() {
		return customValue;
	}
	/**
	 * @param customValue the customValue to set
	 */
	public void setCustomValue(CustomFieldValue[] customValue) {
		this.customValue = customValue;
	}
	/**
	 * @return the declaredAlarmState
	 */
	public AlarmState[] getDeclaredAlarmState() {
		return declaredAlarmState;
	}
	/**
	 * @param declaredAlarmState the declaredAlarmState to set
	 */
	public void setDeclaredAlarmState(AlarmState[] declaredAlarmState) {
		this.declaredAlarmState = declaredAlarmState;
	}
	/**
	 * @return the disabledMethod
	 */
	public String[] getDisabledMethod() {
		return disabledMethod;
	}
	/**
	 * @param disabledMethod the disabledMethod to set
	 */
	public void setDisabledMethod(String[] disabledMethod) {
		this.disabledMethod = disabledMethod;
	}
	/**
	 * @return the effectiveRole
	 */
	public int[] getEffectiveRole() {
		return effectiveRole;
	}
	/**
	 * @param effectiveRole the effectiveRole to set
	 */
	public void setEffectiveRole(int[] effectiveRole) {
		this.effectiveRole = effectiveRole;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the overallStatus
	 */
	public ManagedEntityStatus getOverallStatus() {
		return overallStatus;
	}
	/**
	 * @param overallStatus the overallStatus to set
	 */
	public void setOverallStatus(ManagedEntityStatus overallStatus) {
		this.overallStatus = overallStatus;
	}
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	/**
	 * @return the permission
	 */
	public Permission[] getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Permission[] permission) {
		this.permission = permission;
	}
	/**
	 * @return the recentTask
	 */
	public List<String> getRecentTask() {
		return recentTask;
	}
	/**
	 * @param recentTask the recentTask to set
	 */
	public void setRecentTask(List<String> recentTask) {
		this.recentTask = recentTask;
	}
	/**
	 * @return the tag
	 */
	public Tag[] getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(Tag[] tag) {
		this.tag = tag;
	}
	/**
	 * @return the triggeredAlarmState
	 */
	public AlarmState[] getTriggeredAlarmState() {
		return triggeredAlarmState;
	}
	/**
	 * @param triggeredAlarmState the triggeredAlarmState to set
	 */
	public void setTriggeredAlarmState(AlarmState[] triggeredAlarmState) {
		this.triggeredAlarmState = triggeredAlarmState;
	}
}
