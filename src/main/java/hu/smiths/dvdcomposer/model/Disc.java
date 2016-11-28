package hu.smiths.dvdcomposer.model;

import static hu.smiths.dvdcomposer.model.FolderUtils.getFolderSize;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import hu.smiths.dvdcomposer.model.exceptions.CannotAddFolderToISOImageException;
import hu.smiths.dvdcomposer.model.exceptions.CannotCreateISOFile;
import hu.smiths.dvdcomposer.model.exceptions.TooLargeFolderException;
import hu.smiths.dvdcomposer.utils.ISOWriter;

public final class Disc implements Serializable {

	private static final long serialVersionUID = 2967319287369484755L;

	private DiscGroup group;

	private Set<File> containedFolders;

	private Long freeSpaceInBytes;

	public Disc(DiscGroup group) {
		this.group = group;
		this.containedFolders = new HashSet<File>();
		this.freeSpaceInBytes = group.getSizeInBytes();
	}

	public DiscGroup getGroup() {
		return group;
	}

	public void addFolder(File folder) {
		Integer newFolderSize = getFolderSize(folder);
		if (freeSpaceInBytes < newFolderSize)
			throw new TooLargeFolderException("Folder " + folder.getAbsolutePath() + " is too large for this disc!");
		freeSpaceInBytes -= newFolderSize;
		containedFolders.add(folder);

	}

	public Boolean spaceIsEnough() {
		if (calculateUsedSpace() <= group.getSizeInBytes())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	private Long calculateUsedSpace() {
		Long usedSpaceInBytes = Long.valueOf(0);

		for (File folder : containedFolders) {
			usedSpaceInBytes += getFolderSize(folder);
		}

		return usedSpaceInBytes;
	}

	@Override
	public boolean equals(Object object) {
		return object == this;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	public void addAllFolderToISOWriter(ISOWriter isoWriter) throws CannotAddFolderToISOImageException {
		for (File folder : containedFolders)
			isoWriter.addFolderToISOImage(folder);
	}

	public void createISOFileWithWriter(File output, ISOWriter writer) throws CannotCreateISOFile {
		try {
			addAllFolderToISOWriter(writer);
			writer.dumpContentToFile(output);
		} catch (CannotAddFolderToISOImageException e) {
			throw new CannotCreateISOFile(e);
		}
	}

	public boolean thereIsEnoughSpaceForTheFolder(File folder) {
		return freeSpaceInBytes >= getFolderSize(folder);
	}

	public Set<File> getFolders() {
		return containedFolders;
	}

}
