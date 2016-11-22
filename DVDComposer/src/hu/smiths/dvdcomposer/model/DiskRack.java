package hu.smiths.dvdcomposer.model;

import java.io.Serializable;
import java.util.TreeSet;

public class DiskRack implements Serializable {

	private static final long serialVersionUID = 6525241729717592702L;

	private String name;

	private Integer size;

	private Integer count;

	private Boolean infinity;

	public static DiskRack createFinite(String name, Integer size, Integer count) {
		return new DiskRack(name, size, count, Boolean.FALSE);
	}

	public static DiskRack createInfinite(String name, Integer size) {
		return new DiskRack(name, size, Integer.valueOf(0), Boolean.TRUE);
	}

	protected DiskRack(String name, Integer size, Integer count, Boolean infinity) {
		this.name = name;
		this.size = size;
		this.count = count;
		this.infinity = infinity;
	}

	public String getName() {
		return name;
	}

	public Integer getSize() {
		return size;
	}
	
	public void setCount(Integer newCount){
		this.count = newCount;
		this.infinity = Boolean.FALSE;
	}
	
	public void setCountToInfinite(){
		this.infinity = Boolean.TRUE;
		count = 0;
	}

	public Boolean countIsAtLeast(Integer guessedCount) {
		return infinity || count >= guessedCount;
	}

	public Boolean countIsGreaterThan(Integer guessedCount) {
		return infinity || count > guessedCount;
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject.getClass().equals(getClass())) {
			DiskRack otherType = (DiskRack) otherObject;
			return otherType.name.equals(this.name) && otherType.size.equals(this.size);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return (41 * (41 * (41 * (41 + size.hashCode()) + name.hashCode()) + name.hashCode()) + size.hashCode());
	}
}
