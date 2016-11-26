package hu.smiths.dvdcomposer.model;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hu.smiths.dvdcomposer.model.algorithm.Algorithm;
import hu.smiths.dvdcomposer.model.algorithm.Input;
import hu.smiths.dvdcomposer.model.exceptions.CannotFindValidAssignmentException;
import hu.smiths.dvdcomposer.model.exceptions.InvalidResultException;
import hu.smiths.dvdcomposer.model.exceptions.NotEnoughSpaceOnDiscException;
import hu.smiths.dvdcomposer.model.exceptions.TooManyDiscsInOneGroupException;

public class ConcreteModel implements Model {

	private static final long serialVersionUID = -6640724419687179375L;

	private Set<DiscGroup> discGroups;

	private Set<File> folders;

	private Algorithm algorithm;

	private Set<Disc> generatedDiscs;

	private List<DiscGroup> returnedGroups;

	public ConcreteModel() {
		discGroups = new HashSet<DiscGroup>();
		folders = new HashSet<File>();
	}

	@Override
	public Set<DiscGroup> getDiscGroups() {
		return Collections.unmodifiableSet(discGroups);
	}

	@Override
	public void setDiscGroups(Set<DiscGroup> discGroups) {
		if (validateDiscGroups(discGroups)) {
			this.discGroups = new HashSet<DiscGroup>(discGroups);
		} else {
			throw new IllegalArgumentException(
					"There are at least two disc groups in the passed collection with the same name and size! Disc groups must have unique name - size pairs!");
		}
	}

	private Boolean validateDiscGroups(Collection<DiscGroup> discGroups) {
		Set<DiscGroup> distinctDiscGroups = new HashSet<DiscGroup>(discGroups);
		return distinctDiscGroups.size() == discGroups.size();
	}

	@Override
	public boolean addDiscGroup(DiscGroup group) {
		return discGroups.add(group);
	}

	@Override
	public boolean removeDiscGroup(DiscGroup group) {
		return discGroups.remove(group);
	}

	@Override
	public Set<File> getFolders() {
		return Collections.unmodifiableSet(folders);
	}

	@Override
	public void setFolders(Set<File> folders) {
		this.folders = new HashSet<File>(folders);
	}

	@Override
	public boolean addFolder(File folder) {
		return folders.add(folder);

	}

	@Override
	public boolean removeFolder(File folder) {
		return folders.remove(folder);
	}

	@Override
	public Result generateResult() throws InvalidResultException {
		try {
			generatedDiscs = algorithm.generate(getNewInputForAlgorithm());
			throwsIfGeneratedDiscsContainsInvalidGroup();
			return Result.create(generatedDiscs);
		} catch (NotEnoughSpaceOnDiscException | TooManyDiscsInOneGroupException
				| CannotFindValidAssignmentException e) {
			throw new InvalidResultException(e);
		}
	}

	private void throwsIfGeneratedDiscsContainsInvalidGroup() throws InvalidResultException {
		returnedGroups = getGroupsFromGeneratedDiscs();
		if (!sourceAndGeneratedGroupsAreTheSame()) {
			throw new InvalidResultException(
					"At least one of the generated discs contains a group that is not in the source groups!");
		}
	}

	private boolean sourceAndGeneratedGroupsAreTheSame() {
		return passedGroupsContainAllReturnedGroup() && thereAreLessOrEqualsReturnedGroup()
				&& everyFieldOfReturnedGroupsAreTheSameAsPassedGroups();
	}

	private boolean everyFieldOfReturnedGroupsAreTheSameAsPassedGroups() {
		for (DiscGroup passedGroup : discGroups) {
			if (!everyEqualReturnedGroupsAreTheAse(passedGroup)) {
				return false;
			}
		}
		return true;
	}

	private boolean everyEqualReturnedGroupsAreTheAse(DiscGroup passedGroup) {
		for (DiscGroup returnedGroup : returnedGroups) {
			if (passedGroup.equals(returnedGroup) && !passedGroup.allFieldsAreEquals(returnedGroup))
				return false;
		}
		return true;
	}

	private boolean thereAreLessOrEqualsReturnedGroup() {
		Set<DiscGroup> distinctReturnedGroups = new HashSet<DiscGroup>(returnedGroups);
		return discGroups.size() >= distinctReturnedGroups.size();
	}

	private boolean passedGroupsContainAllReturnedGroup() {
		return discGroups.containsAll(returnedGroups);
	}

	private List<DiscGroup> getGroupsFromGeneratedDiscs() {
		return generatedDiscs.stream().map(d -> d.getGroup()).collect(Collectors.toList());
	}

	private Input getNewInputForAlgorithm() {
		return new Input(discGroups.stream().map(dc -> dc.clone()).collect(Collectors.toSet()), folders);
	}

	@Override
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

}
