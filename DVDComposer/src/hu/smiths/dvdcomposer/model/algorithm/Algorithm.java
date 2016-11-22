package hu.smiths.dvdcomposer.model.algorithm;

import java.util.Set;

import hu.smiths.dvdcomposer.model.Disc;
import hu.smiths.dvdcomposer.model.exceptions.CannotFindValidAssignmentException;

public interface Algorithm {
	
	public Set<Disc> generate(Input input) throws CannotFindValidAssignmentException;
}
