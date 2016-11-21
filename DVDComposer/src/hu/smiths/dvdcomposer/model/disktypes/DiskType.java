package hu.smiths.dvdcomposer.model.disktypes;

import java.io.Serializable;

public class DiskType implements Serializable {

	private static final long serialVersionUID = 6525241729717592702L;
	
	private static Integer globalId = 0;

	private String name;
	
	private Integer size;
	
	private Integer count;
	private final Integer id;
	
	private Boolean infinity;
	
	DiskType(String name, Integer size, Integer count, Boolean infinity){
		this.name = name;
		this.size = size;
		this.count = count;
		this.infinity = infinity;
		synchronized (globalId) {
			id = globalId;
			globalId++;
		}
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
	
	@Override
	public boolean equals(Object otherObject){
		if (otherObject.getClass().equals(getClass())){
			DiskType otherType = (DiskType) otherObject;
			return otherType.id.equals(this.id);
		} else
			return false;
	}
}
