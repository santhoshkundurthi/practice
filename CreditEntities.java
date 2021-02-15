package com.anz.pojo;

import java.util.Optional;

public class CreditEntities {

	Optional<String> entity = null;
	Optional<String> parent =null;

	Optional<Integer> limit;
	Optional<Integer> utilisation;
	public Optional<String> getEntity() {
		return entity;
	}
	public void setEntity(Optional<String> entity) {
		this.entity = entity;
	}
	public Optional<String> getParent() {
		return parent;
	}
	public void setParent(Optional<String> parent) {
		this.parent = parent;
	}
	public Optional<Integer> getLimit() {
		return limit;
	}
	public void setLimit(Optional<Integer> limit) {
		this.limit = limit;
	}
	public Optional<Integer> getUtilisation() {
		return utilisation;
	}
	public void setUtilisation(Optional<Integer> utilisation) {
		this.utilisation = utilisation;
	}
	@Override
	public String toString() {
		return "[entity=" + entity + ", parent=" + parent + ", limit=" + limit + ", utilisation="
				+ utilisation + "]";
	}

	
	
}
