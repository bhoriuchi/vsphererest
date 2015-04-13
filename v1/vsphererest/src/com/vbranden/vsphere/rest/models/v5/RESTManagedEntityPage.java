package com.vbranden.vsphere.rest.models.v5;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Branden Horiuchi (bhoriuchi@gmail.com)
 * @version 5
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTManagedEntityPage {

	private URI previous;
	private URI next;
	private int pageNumber;
	private int pageSize;
	private int totalItems;
	public List<Object> items;
	private URI resource;

	public RESTManagedEntityPage() {
	}

	public RESTManagedEntityPage(URI previous, URI next, int pageNumber,
			int pageSize, int totalItems, List<Object> items,
			URI resource) {
		this.setPrevious(previous);
		this.setNext(next);
		this.setPageNumber(pageNumber);
		this.setPageSize(pageSize);
		this.setTotalItems(totalItems);
		this.setItems(items);
		this.setResource(next);

	}

	/**
	 * @return the previous
	 */
	public URI getPrevious() {
		return previous;
	}

	/**
	 * @param previous
	 *            the previous to set
	 */
	public void setPrevious(URI previous) {
		this.previous = previous;
	}

	/**
	 * @return the next
	 */
	public URI getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(URI next) {
		this.next = next;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @return the resource
	 */
	public URI getResource() {
		return resource;
	}

	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setResource(URI resource) {
		this.resource = resource;
	}

	/**
	 * @return the items
	 */
	public List<Object> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Object> items) {
		this.items = items;
	}

	/**
	 * @return the totalItems
	 */
	public int getTotalItems() {
		return totalItems;
	}

	/**
	 * @param totalItems the totalItems to set
	 */
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

}
