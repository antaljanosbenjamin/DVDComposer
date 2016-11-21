package hu.smiths.dvdcomposer.model.disktypes;

public class DVDType extends DiskType {

	private static final long serialVersionUID = -3757601498807680311L;

	DVDType(Integer count, Boolean infinity){
		super("DVD", 4700, count, infinity);
	}
}
