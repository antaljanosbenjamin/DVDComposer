package hu.smiths.dvdcomposer.model.algorithm;

import static hu.smiths.dvdcomposer.model.FolderUtils.getFolderSize;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hu.smiths.dvdcomposer.model.Disc;
import hu.smiths.dvdcomposer.model.DiscGroup;
import hu.smiths.dvdcomposer.model.exceptions.CannotFindValidAssignmentException;

public class GreedyAlgorithm implements Algorithm {

	private List<DiscGroup> discGroups;

	private DiscGroup currentGroup;

	private List<File> folders;

	private Set<Disc> generatedDiscs;

	private Map<DiscGroup, Integer> countInGroups;

	@Override
	public Set<Disc> generate(Input input) throws CannotFindValidAssignmentException {
		throwsIfInputIsInvalid(input);
		initialize(input);
		assignFoldersToDiscs();
		return generatedDiscs;
	}

	private void initialize(Input input) {
		discGroups = new ArrayList<DiscGroup>(input.getDiscGroups());
		folders = new ArrayList<File>(input.getFolders());
		sortFolders();
		generatedDiscs = new HashSet<Disc>();
		currentGroup = discGroups.get(0);
		countInGroups = new HashMap<DiscGroup, Integer>();
	}

	private void sortFolders() {
		Collections.sort(folders, new Comparator<File>() {
			@Override
			public int compare(File lhs, File rhs) {
				return getFolderSize(rhs).compareTo(getFolderSize(lhs));
			}
		});

	}

	private void throwsIfInputIsInvalid(Input input) {
		throwsIsInputDoesntContainsAnyDiscGroup(input.getDiscGroups());
		throwsIsInputDoesntContainsAnyFolder(input.getFolders());

	}

	private void throwsIsInputDoesntContainsAnyFolder(Set<File> folders) {
		if (folders.size() == 0)
			throw new IllegalArgumentException("Input must have contains at least one folder!");
	}

	private void throwsIsInputDoesntContainsAnyDiscGroup(Set<DiscGroup> discGroups) {
		if (discGroups.size() == 0)
			throw new IllegalArgumentException("Input must have contains at least one disc group!");
	}

	private void assignFoldersToDiscs() throws CannotFindValidAssignmentException {
		for (File folder : folders) {
			if (thereIsEnoughSpaceForFolderInAnyDisc(folder)) {
				assignFolderToExistingDisc(folder);
			} else {
				assignFolderToNewDisc(folder);
			}
		}
	}

	private boolean thereIsEnoughSpaceForFolderInAnyDisc(File folder) {
		for (Disc disc : generatedDiscs) {
			if (disc.thereIsEnoughSpaceForTheFolder(folder))
				return true;
		}
		return false;
	}

	private void assignFolderToNewDisc(File folder) throws CannotFindValidAssignmentException {
		adjustGroupForFolder(folder);
		generateNewDiscFromCurrentGroup().addFolder(folder);
	}

	private void assignFolderToExistingDisc(File folder) {
		for (Disc disc : generatedDiscs) {
			if (disc.thereIsEnoughSpaceForTheFolder(folder)) {
				disc.addFolder(folder);
				return;
			}
		}
	}

	private void adjustGroupForFolder(File folder) throws CannotFindValidAssignmentException {
		for (DiscGroup group : discGroups) {
			if (isRightGroupForFolder(group, folder)){
				currentGroup = group;
				return;
			}
		}
		throw new CannotFindValidAssignmentException(
				"Cannot find right group for " + folder.getAbsolutePath() + " folder! Size: " + getFolderSize(folder));
	}

	private boolean isRightGroupForFolder(DiscGroup group, File folder) {
		return group.haveMoreThan(getCountInGroup(group)) && (group.getSizeInBytes() >= getFolderSize(folder));
	}

	private Disc generateNewDiscFromCurrentGroup() {
		Disc newDisc = new Disc(currentGroup);
		generatedDiscs.add(newDisc);
		incrementDiscCountInCurrentGroup();
		return newDisc;
	}

	private void incrementDiscCountInCurrentGroup() {
		countInGroups.put(currentGroup, getCountInGroup(currentGroup) + 1);

	}

	private Integer getCountInGroup(DiscGroup guessedGroup) {
		return countInGroups.getOrDefault(currentGroup, Integer.valueOf(0));
	}

}
