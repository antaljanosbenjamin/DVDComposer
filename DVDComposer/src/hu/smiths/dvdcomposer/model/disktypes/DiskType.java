package hu.smiths.dvdcomposer.model.disktypes;

import java.io.Serializable;
import java.util.TreeSet;

public class DiskType implements Serializable {

	private static final long serialVersionUID = 6525241729717592702L;

	private String name;

	private Integer size;

	private Integer count;

	private Boolean infinity;

	public static DiskType createFinite(String name, Integer size, Integer count) {
		return new DiskType(name, size, count, Boolean.FALSE);
	}

	public static DiskType createInfinite(String name, Integer size) {
		return new DiskType(name, size, Integer.valueOf(0), Boolean.TRUE);
	}

	protected DiskType(String name, Integer size, Integer count, Boolean infinity) {
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

	public Boolean countIsAtLeast(Integer guessedCount) {
		return infinity || count >= guessedCount;
	}

	public Boolean countIsGreater(Integer guessedCount) {
		return infinity || count > guessedCount;
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject.getClass().equals(getClass())) {
			DiskType otherType = (DiskType) otherObject;
			return otherType.name.equals(this.name) && otherType.size.equals(this.size);
		} else
			return false;
	}
}
