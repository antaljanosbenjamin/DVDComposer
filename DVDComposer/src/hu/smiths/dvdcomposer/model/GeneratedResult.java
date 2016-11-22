package hu.smiths.dvdcomposer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hu.smiths.dvdcomposer.model.exceptions.NotEnoughSpaceOnDiscException;
import hu.smiths.dvdcomposer.model.exceptions.TooManyDiscsInOneGroupException;

public class GeneratedResult {

	private Set<Disc> discs;

	public static GeneratedResult create(Set<Disc> discs)
			throws NotEnoughSpaceOnDiscException, TooManyDiscsInOneGroupException {
		checkDiscSet(discs);
		return new GeneratedResult(discs);
	}

	private GeneratedResult(Set<Disc> discs) {

		this.discs = discs;
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

	private static void checkDiscCountInGroups(Set<Disc> discs) throws TooManyDiscsInOneGroupException {

		Map<DiscGroup, Integer> discCountInGroups = new HashMap<DiscGroup, Integer>();

		for (Disc disc : discs) {
			if (discCountInGroups.containsKey(disc.getGroup())) {
				incrementsDiscCountInGroup(discCountInGroups, disc.getGroup());
			}
		}

		for (Map.Entry<DiscGroup, Integer> entry : discCountInGroups.entrySet()) {
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
