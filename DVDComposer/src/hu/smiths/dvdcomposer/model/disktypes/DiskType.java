package hu.smiths.dvdcomposer.model.disktypes;

import java.io.Serializable;

public class DiskType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Integer size;
	
	private Integer count;
	
	private Boolean infinity;
	
	DiskType(String name, Integer size, Integer count, Boolean infinity){
		this.name = name;
		this.size = size;
		this.count = count;
		this.infinity = infinity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getInfinity() {
		return infinity;
	}

	public void setInfinity(Boolean infinity) {
		this.infinity = infinity;
	}
}
