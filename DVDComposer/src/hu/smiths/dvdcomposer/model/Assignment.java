package hu.smiths.dvdcomposer.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hu.smiths.dvdcomposer.model.exceptions.NotEnoughSpaceOnDiscException;
import hu.smiths.dvdcomposer.model.exceptions.TooManyDiscsInOneGroupException;

public class Assignment {

	private Set<Disc> discs;

	public static Assignment create(Set<Disc> discs)
			throws NotEnoughSpaceOnDiscException, TooManyDiscsInOneGroupException {
		checkDiscSet(discs);
		return new Assignment(discs);
	}

	private Assignment(Set<Disc> discs) {

		this.discs = new HashSet<Disc>(discs);
	}

	private static void checkDiscSet(Set<Disc> discs)
			throws NotEnoughSpaceOnDiscException, TooManyDiscsInOneGroupException {
		checkDiscsSize(discs);
		checkDiscCountInGroups(discs);
	}

	private static void checkDiscsSize(Set<Disc> discs) throws NotEnoughSpaceOnDiscException {
		for (Disc disc : discs) {
			if (!disc.spaceIsEnough())
				throw new NotEnoughSpaceOnDiscException(
						"Not enought space in one of " + disc.getGroup().getName() + " discs!");
		}
	}
	
	private static Map<DiscGroup, Integer> getCountsByGroup(Set<Disc> discs){
		Map<DiscGroup, Integer> discCountsInGroups = new HashMap<DiscGroup, Integer>();

		for (Disc disc : discs) {
			if (discCountsInGroups.containsKey(disc.getGroup())) {
				incrementsDiscCountInGroup(discCountsInGroups, disc.getGroup());
			}
		}
		
		return discCountsInGroups;
	}
	

	private static void checkDiscCountInGroups(Set<Disc> discs) throws TooManyDiscsInOneGroupException {

		Map<DiscGroup, Integer> countsByGroup = getCountsByGroup(discs);

		for (Map.Entry<DiscGroup, Integer> entry : countsByGroup.entrySet()) {
			DiscGroup group = entry.getKey();
			Integer countInGroup = entry.getValue();
			if (!group.haveAtLeast(countInGroup)) {
				throw new TooManyDiscsInOneGroupException("Too more discs in " + group.getName() + " group!");
			}
		}
	}

	private static void incrementsDiscCountInGroup(Map<DiscGroup, Integer> values, DiscGroup group) {
		values.put(group, values.get(group) + 1);
	}

	public Set<Disc> getDiscs() {
		return discs;
	}
}
