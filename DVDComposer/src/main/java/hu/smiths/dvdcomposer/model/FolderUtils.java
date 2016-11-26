package hu.smiths.dvdcomposer.model;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class FolderUtils {
	
	public static Integer getFolderSize(File folder){
		if (!folder.isDirectory())
			throw new IllegalArgumentException(folder.getAbsolutePath() + " is not a folder!");
		return (int) FileUtils.sizeOfDirectory(folder);
	}
}
