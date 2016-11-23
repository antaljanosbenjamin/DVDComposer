package hu.smiths.algorithms.binpacking;

import java.util.Set;

import hu.smiths.dvdcomposer.model.Disc;
import hu.smiths.dvdcomposer.model.algorithm.Algorithm;
import hu.smiths.dvdcomposer.model.algorithm.Input;
import hu.smiths.dvdcomposer.model.exceptions.CannotFindValidAssignmentException;

public class OuterGreedyAlgorithm implements Algorithm{

	@Override
	public Set<Disc> generate(Input input) throws CannotFindValidAssignmentException {
		throw new CannotFindValidAssignmentException("NOT IMPLEMENTED!");
	}

}
