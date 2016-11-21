package hu.smiths.dvdcomposer.model.disktypes;

public class BlueRayType extends DiskType {
	
	private static final long serialVersionUID = -1461862011298215705L;

	BlueRayType(Integer count, Boolean infinity){
		super("BlueRay", 2500, count, infinity);
	}

}
