package hu.smiths.dvdcomposer.model;

import java.io.Serializable;

public class Disc implements Serializable {

	private static final long serialVersionUID = 2967319287369484755L;

	private DiscGroup group;

	public Disc(DiscGroup rack) {
		this.group = rack;
	}

	public DiscGroup getGroup() {
		return group;
	}

	public void setGroup(DiscGroup rack) {
		this.group = rack;
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
