package hu.smiths.dvdcomposer.model;

import java.io.Serializable;

public class Disk implements Serializable {

	private static final long serialVersionUID = 2967319287369484755L;

	private DiskType type;

	public Disk(DiskType type) {
		this.type = type;
	}

	public DiskType getType() {
		return type;
	}

	public void setType(DiskType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object object) {
		return object == this;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

}
