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


import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.List;

import com.vbranden.vsphere.rest.helpers.FieldGet;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceArray;
import com.vbranden.vsphere.rest.helpers.ManagedObjectReferenceUri;
import com.vmware.vim25.LocalizableMessage;
import com.vmware.vim25.LocalizedMethodFault;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.TaskReason;

/**
* @author Branden Horiuchi (bhoriuchi@gmail.com)
* @version 5
*/

public class RESTTaskInfo extends RESTDynamicData {
	
	private boolean cancelable;
	private boolean cancelled;
	private String changeTag;
	private Calendar completeTime;
	private LocalizableMessage description;
	private String descriptionId;
	private String entity;
	private String entityName;
	private LocalizedMethodFault error;
	private int eventChainId;
	private String key;
	private List<String> locked;
	private String name;
	private String parentTaskKey;
	private int progress;
	private Calendar queueTime;
	private TaskReason reason;
	private Object result;
	private String rootTaskKey;
	private Calendar startTime;
	private TaskInfoState state;
	private String task;
	
	
	
	// constructor
	public RESTTaskInfo() {
	}

	// overloaded constructor
	public RESTTaskInfo(TaskInfo mo, String uri, String fields) {
		this.init(mo, uri, fields);
	}

	public void init(TaskInfo mo, String uri, String fields) {

		fields = "all";
		
		// to speed performance, only get field data that was requested
		FieldGet fg = new FieldGet();
		
		try {
			
			// specific fields	
			if (fg.get("cancelable", fields)) {
				this.setCancelable(mo.isCancelable());
			}	
			if (fg.get("cancelled", fields)) {
				this.setCancelled(mo.isCancelled());
			}	
			if (fg.get("changeTag", fields)) {
				this.setChangeTag(mo.getChangeTag());
			}	
			if (fg.get("completeTime", fields)) {
				this.setCompleteTime(mo.getCompleteTime());
			}	
			if (fg.get("description", fields)) {
				this.setDescription(mo.getDescription());
			}	
			if (fg.get("descriptionId", fields)) {
				this.setDescriptionId(mo.getDescriptionId());
			}	
			if (fg.get("entity", fields)) {
				this.setEntity(new ManagedObjectReferenceUri().getUri(mo.getEntity(), uri));
			}	
			if (fg.get("entityName", fields)) {
				this.setEntityName(mo.getEntityName());
			}	
			if (fg.get("error", fields)) {
				this.setError(mo.getError());
			}	
			if (fg.get("eventChainId", fields)) {
				this.setEventChainId(mo.getEventChainId());
			}	
			if (fg.get("key", fields)) {
				this.setKey(mo.getKey());
			}	
			if (fg.get("locked", fields)) {
				this.setLocked(new ManagedObjectReferenceArray().getMORArray(mo.getLocked(), uri));
			}	
			if (fg.get("name", fields)) {
				this.setName(mo.getName());
			}	
			if (fg.get("parentTaskKey", fields)) {
				this.setParentTaskKey(mo.getParentTaskKey());
			}	
			if (fg.get("progress", fields)) {
				if (mo.getProgress() == null) {
					this.setProgress(-1);
				}
				else {
					this.setProgress(mo.getProgress());
				}
			}	
			if (fg.get("queueTime", fields)) {
				this.setQueueTime(mo.getQueueTime());
			}	
			if (fg.get("reason", fields)) {
				this.setReason(mo.getReason());
			}	
			if (fg.get("result", fields)) {
				this.setResult(mo.getResult());
			}	
			if (fg.get("rootTaskKey", fields)) {
				this.setRootTaskKey(mo.getRootTaskKey());
			}	
			if (fg.get("startTime", fields)) {
				this.setStartTime(mo.getStartTime());
			}	
			if (fg.get("state", fields)) {
				this.setState(mo.getState());
			}	
			if (fg.get("task", fields)) {
				this.setTask(new ManagedObjectReferenceUri().getUri(mo.getTask(), uri));
			}			
			
			
			// extended from RESTDynamicData
			if (fg.get("dynamicProperty", fields)) {
				this.setDynamicProperty(mo.getDynamicProperty());
			}
			if (fg.get("dynamicType", fields)) {
				this.setDynamicType(mo.getDynamicType());
			}
			
			
		} catch (InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the cancelable
	 */
	public boolean isCancelable() {
		return cancelable;
	}

	/**
	 * @param cancelable the cancelable to set
	 */
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	/**
	 * @return the cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * @param cancelled the cancelled to set
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * @return the changeTag
	 */
	public String getChangeTag() {
		return changeTag;
	}

	/**
	 * @param changeTag the changeTag to set
	 */
	public void setChangeTag(String changeTag) {
		this.changeTag = changeTag;
	}

	/**
	 * @return the completeTime
	 */
	public Calendar getCompleteTime() {
		return completeTime;
	}

	/**
	 * @param completeTime the completeTime to set
	 */
	public void setCompleteTime(Calendar completeTime) {
		this.completeTime = completeTime;
	}

	/**
	 * @return the description
	 */
	public LocalizableMessage getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(LocalizableMessage description) {
		this.description = description;
	}

	/**
	 * @return the descriptionId
	 */
	public String getDescriptionId() {
		return descriptionId;
	}

	/**
	 * @param descriptionId the descriptionId to set
	 */
	public void setDescriptionId(String descriptionId) {
		this.descriptionId = descriptionId;
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @return the error
	 */
	public LocalizedMethodFault getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(LocalizedMethodFault error) {
		this.error = error;
	}

	/**
	 * @return the eventChainId
	 */
	public int getEventChainId() {
		return eventChainId;
	}

	/**
	 * @param eventChainId the eventChainId to set
	 */
	public void setEventChainId(int eventChainId) {
		this.eventChainId = eventChainId;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the locked
	 */
	public List<String> getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(List<String> locked) {
		this.locked = locked;
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
	 * @return the parentTaskKey
	 */
	public String getParentTaskKey() {
		return parentTaskKey;
	}

	/**
	 * @param parentTaskKey the parentTaskKey to set
	 */
	public void setParentTaskKey(String parentTaskKey) {
		this.parentTaskKey = parentTaskKey;
	}

	/**
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * @param progress the progress to set
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
	 * @return the queueTime
	 */
	public Calendar getQueueTime() {
		return queueTime;
	}

	/**
	 * @param queueTime the queueTime to set
	 */
	public void setQueueTime(Calendar queueTime) {
		this.queueTime = queueTime;
	}

	/**
	 * @return the reason
	 */
	public TaskReason getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(TaskReason reason) {
		this.reason = reason;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the rootTaskKey
	 */
	public String getRootTaskKey() {
		return rootTaskKey;
	}

	/**
	 * @param rootTaskKey the rootTaskKey to set
	 */
	public void setRootTaskKey(String rootTaskKey) {
		this.rootTaskKey = rootTaskKey;
	}

	/**
	 * @return the startTime
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the state
	 */
	public TaskInfoState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(TaskInfoState state) {
		this.state = state;
	}

	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}





}
