package hu.smiths.dvdcomposer.model.disktypes;

public class CDType extends DiskType {

	private static final long serialVersionUID = 4129750951878540092L;

	CDType(Integer count, Boolean infinity) {
		super("CD", 4700, count, infinity);
	}
}
