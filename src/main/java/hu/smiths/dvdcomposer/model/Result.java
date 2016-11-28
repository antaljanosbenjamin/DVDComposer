package hu.smiths.dvdcomposer.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hu.smiths.dvdcomposer.model.exceptions.CannotCreateISOFile;
import hu.smiths.dvdcomposer.model.exceptions.NotEnoughSpaceOnDiscException;
import hu.smiths.dvdcomposer.model.exceptions.TooManyDiscsInOneGroupException;
import hu.smiths.dvdcomposer.utils.ISOOptions;
import hu.smiths.dvdcomposer.utils.ISOWriter;

public class Result {

	private Set<Disc> discs;

	private ISOWriter writer;

	private ISOOptions isoOptions;

	public static Result create(Set<Disc> discs) throws NotEnoughSpaceOnDiscException, TooManyDiscsInOneGroupException {
		throwExceptionIfDiscSetIsInvalid(discs);
		return new Result(discs);
	}

	private Result(Set<Disc> discs) {

		this.discs = new HashSet<Disc>(discs);
	}

	private static void throwExceptionIfDiscSetIsInvalid(Set<Disc> discs)
			throws NotEnoughSpaceOnDiscException, TooManyDiscsInOneGroupException {
		throwExceptionIfThereAreTooMuchUsedSpaceOnAnyDisc(discs);
		throwExceptionIfThereAreTooManyDiscsFromAnyGroup(discs);
	}

	private static void throwExceptionIfThereAreTooMuchUsedSpaceOnAnyDisc(Set<Disc> discs)
			throws NotEnoughSpaceOnDiscException {
		for (Disc disc : discs) {
			if (!disc.spaceIsEnough())
				throw new NotEnoughSpaceOnDiscException(
						"Not enought space in one of " + disc.getGroup().getName() + " discs!");
		}
	}

	private static Map<DiscGroup, Integer> getCountsByGroup(Set<Disc> discs) {
		Map<DiscGroup, Integer> discCountsInGroups = new HashMap<DiscGroup, Integer>();

		for (Disc disc : discs) {
			if (discCountsInGroups.containsKey(disc.getGroup())) {
				incrementsDiscCountInGroup(discCountsInGroups, disc.getGroup());
			} else {
				discCountsInGroups.put(disc.getGroup(), Integer.valueOf(1));
			}
		}

		return discCountsInGroups;
	}

	private static void throwExceptionIfThereAreTooManyDiscsFromAnyGroup(Set<Disc> discs)
			throws TooManyDiscsInOneGroupException {

		Map<DiscGroup, Integer> countsByGroup = getCountsByGroup(discs);
		for (Map.Entry<DiscGroup, Integer> entry : countsByGroup.entrySet()) {
			DiscGroup group = entry.getKey();
			Integer countInGroup = entry.getValue();
			if (!group.haveAtLeast(countInGroup)) {
				throw new TooManyDiscsInOneGroupException("Too many discs in " + group.getName() + " group!");
			}
		}
	}

	private static void incrementsDiscCountInGroup(Map<DiscGroup, Integer> values, DiscGroup group) {
		Integer oldValue = values.get(group);
		values.put(group, oldValue == null ? 1 : oldValue + 1);
	}

	public Set<Disc> getDiscs() {
		return discs;
	}

	public void generateISOFiles(ISOOptions options) throws CannotCreateISOFile {
		writer = new ISOWriter();
		isoOptions = options;
		int discNumber = 1;
		for (Disc disc : discs) {
			disc.createISOFileWithWriter(createOutputFileFromDiscNumberAndGroup(discNumber++, disc.getGroup()), writer);
		}
	}

	private File createOutputFileFromDiscNumberAndGroup(int discNumber, DiscGroup group) {
		return new File(
				isoOptions.pathToTargetDirectory + "/" + isoOptions.prefix + "_" + group.getName() + "_" + discNumber +" .iso");
	}
}
