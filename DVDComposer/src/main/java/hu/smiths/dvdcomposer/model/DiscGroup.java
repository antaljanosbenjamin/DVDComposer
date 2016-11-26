package hu.smiths.dvdcomposer.model;

import java.io.Serializable;

public final class DiscGroup implements Serializable {

	private static final long serialVersionUID = 6525241729717592702L;

	private String name;

	private Long sizeInBytes;

	private Integer count;

	private Boolean infinity;

	public static DiscGroup createFinite(String name, Long size, Integer count) {
		if (count < 1) {
			throw new IllegalArgumentException("Count must be greater than zero!");
		}
		return new DiscGroup(name, size, count, Boolean.FALSE);
	}

	public static DiscGroup createInfinite(String name, Long size) {
		return new DiscGroup(name, size, Integer.valueOf(-1), Boolean.TRUE);
	}

	protected DiscGroup(String name, Long size, Integer count, Boolean infinity) {
		this.name = name;
		this.sizeInBytes = size;
		this.count = count;
		this.infinity = infinity;
	}

	public String getName() {
		return name;
	}

	public Long getSizeInBytes() {
		return sizeInBytes;
	}

	public Integer getCount() {
		return count;
	}

	public boolean getInfinity() {
		return infinity;
	}

	public void setCount(Integer newCount) {
		if (newCount < 1) {
			throw new IllegalArgumentException("Count must be greater than zero!");
		}
		this.count = newCount;
		this.infinity = Boolean.FALSE;
	}

	public void setCountToInfinite() {
		this.infinity = Boolean.TRUE;
		count = -1;
	}

	public Boolean haveAtLeast(Integer guessedCount) {
		return infinity || count >= guessedCount;
	}

	public Boolean haveMoreThan(Integer guessedCount) {
		return infinity || count > guessedCount;
	}

	public boolean allFieldsAreEquals(DiscGroup otherGroup) {
		return name.equals(otherGroup.name) && sizeInBytes.equals(otherGroup.sizeInBytes)
				&& count.equals(otherGroup.count) && infinity.equals(otherGroup.infinity);
	}

	@Override
	public DiscGroup clone() {
		return new DiscGroup(name, sizeInBytes, count, infinity);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject.getClass().equals(getClass())) {
			DiscGroup otherType = (DiscGroup) otherObject;
			return otherType.name.equals(this.name) && otherType.sizeInBytes.equals(this.sizeInBytes);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return (41 * (41 * (41 * (41 + sizeInBytes.hashCode()) + name.hashCode()) + name.hashCode())
				+ sizeInBytes.hashCode());
	}
}
